//
//  OCREScanner.m
//  OCRE
//
//  Created by Kevin M Free on 12/10/15.
//  Copyright © 2015 CTB Consulting. All rights reserved.
//

#import "OCREScanner.h"
#import "CardLayouts.h"
#import <AVFoundation/AVFoundation.h>



@implementation OCREScanner : NSObject

@synthesize operationQueue;
@synthesize currentOperationsCancelled;
@synthesize foundCardInfo;
@synthesize alertShowing;

int currentScanDigits;

-(id)initScanner
{
  self = [super init];
  self.operationQueue = [[NSOperationQueue alloc] init];
  alertShowing = NO;
  return self;
}


-(void)recognizeImageWithTesseract:(UIImage *)image
{
  /*
   * Debug code
   */
  //UIImageWriteToSavedPhotosAlbum(image, nil, nil, nil);
  
  [self setTorchOn:false];
  currentOperationsCancelled = NO;
  foundCardInfo = NO;
  
  CardLayouts *layouts = [[CardLayouts alloc] initCardLayouts];
  for (CardDefinition *cd in layouts.CardDefinitions)
  {
    if(foundCardInfo)
    {
      break;
    }
    UIImage * imageToScan = image;
    if(cd.RotateLeft)
    {
      NSLog(@"Rotating -90d");
      imageToScan = [self rotateUIImage:image clockwise:NO];
      //UIImageWriteToSavedPhotosAlbum(imageToScan, nil, nil, nil);
    }
    
    currentScanDigits = cd.Digits;
    NSLog(@"Scan Started...");
    
    NSMutableString *currentCCN = [NSMutableString new];
    NSMutableString *currentCSC = [NSMutableString new];
    NSMutableString *currentExpiry = [NSMutableString new];
    currentOperationsCancelled = NO;
    [self addScanOperation:imageToScan : cd.CCNCoordinatesR0 : CCN: currentCCN: currentCSC : currentExpiry: NO];
    [self addScanOperation:imageToScan : cd.CCNCoordinatesR1 : CCN: currentCCN: currentCSC : currentExpiry: NO];
    [self addScanOperation:imageToScan : cd.CCNCoordinatesR2 : CCN: currentCCN: currentCSC : currentExpiry: NO];
    [self addScanOperation:imageToScan : cd.CCNCoordinatesR3 : CCN: currentCCN: currentCSC : currentExpiry: YES];
    
    
    
  }
  
  
}


//TODO: make these parameters some type of result struct
- (void) addScanOperation: (UIImage *)image :(CGRect) scanAreaRect : (ScanType) scanType : (NSMutableString *) currentCCN : (NSMutableString *) currentCSC : (NSMutableString *) currentExpiry :(BOOL) isFinalOperation
{
  if(currentOperationsCancelled)
  {
    NSLog(@"Operation Cancelled!");
    return;
  }
  
  if(CGRectIsEmpty(scanAreaRect) || image == nil)
  {
    if(isFinalOperation)
    {
      [self finishedScanning:currentCCN : currentCSC: currentExpiry];
    }
    return;
  }
  
  // Create a new `G8RecognitionOperation` to perform the OCR asynchronously
  // It is assumed that there is a .traineddata file for the language pack
  // you want Tesseract to use in the "tessdata" folder in the root of the
  // project AND that the "tessdata" folder is a referenced folder and NOT
  // a symbolic group in your project
  G8RecognitionOperation *operation = [[G8RecognitionOperation alloc] initWithLanguage:@"eng"];
                                                                      

  
//[operation.tesseract setValue:@"user-words" forKey:kG8ParamUserWordsSuffix];
  // Use the original Tesseract engine mode in performing the recognition
  // (see G8Constants.h) for other engine mode options
  operation.tesseract.engineMode = G8OCREngineModeTesseractOnly;
  
  
  // Let Tesseract automatically segment the page into blocks of text
  // based on its analysis (see G8Constants.h) for other page segmentation
  // mode options
  operation.tesseract.pageSegmentationMode = G8PageSegmentationModeSingleWord;//G8PageSegmentationModeSingleWord;//G8PageSegmentationModeAutoOnly;//G8PageSegmentationModeSingleWord;//
  
  // Optionally limit the time Tesseract should spend performing the
  // recognition
  operation.tesseract.maximumRecognitionTime = 1.0;
  
  // Set the delegate for the recognition to be this class
  // (see `progressImageRecognitionForTesseract` and
  // `shouldCancelImageRecognitionForTesseract` methods below)
  operation.delegate = self;
  

  // Set the image on which Tesseract should perform recognition
  operation.tesseract.image = image;
  
  // We only want numbers and slash. Trained data should not contain anything else
  operation.tesseract.charWhitelist = @"0123456789/";
  operation.tesseract.rect = scanAreaRect;

  // Specify the function block that should be executed when Tesseract
  // finishes performing recognition on the image
  operation.recognitionCompleteBlock = ^(G8Tesseract *tesseract)
  {
    // Fetch the recognized text
    NSString *recognizedText = tesseract.recognizedText;
    
    NSLog(@"%@", recognizedText);
    NSMutableString * creditCardInfo = [NSMutableString new];
    //[creditCardInfo appendFormat:@"%s", ""];
    static int i = 0;
    creditCardInfo = [self scanInfo:creditCardInfo : recognizedText : currentScanDigits : i];
    
    //we know if we dont find anything in a scan that we cannot complete the others
    if(creditCardInfo.length <4)
    {
      currentOperationsCancelled = YES;
      NSLog(@"Operation Cancelled!");
      return;
    }
    
  
    
    switch (scanType) {
      case CCN:
      {
        [currentCCN appendFormat:@"%@", creditCardInfo];
      }
        break;
      case CSC:
      {
        [currentCSC appendFormat:@"%@", creditCardInfo];
      }
        break;
      case Expiry:
      {
        [currentExpiry appendFormat:@"%@", creditCardInfo];
      }
        break;
      default:
        break;
    }
    
    //Debug code:
    //UIImageWriteToSavedPhotosAlbum(operation.tesseract.thresholdedImage, nil, nil, nil);
    
    if(isFinalOperation)
    {
      [self finishedScanning:currentCCN : currentCSC: currentExpiry];
    }
    
    
  };

  [self.operationQueue addOperation:operation];
}

-(void) finishedScanning : (NSMutableString *) currentCCN  : (NSMutableString *) currentCSC  : (NSMutableString *) currentExpiry
{
  NSLog(@"Scan Finished.");
  
  NSRange range = [currentCCN rangeOfString:@"/"];
  // we don't do partial data
  if(currentCCN.length != currentScanDigits|| range.location != NSNotFound)
  {
    return;
  }
  range = [currentExpiry rangeOfString:@"/"];
  if(range.location == NSNotFound)
  {
    currentExpiry = [NSMutableString new];
  }
  if(currentCSC.length != 3)
  {
    currentCSC = [NSMutableString new];;
  }
  
  
  //Debug Code
  
  NSMutableString *debugString = [NSMutableString new];
  [debugString appendFormat:@"%@", @"CCN: "];
  //[debugString appendFormat:@"%@", @"\n CSC: "];
  //[debugString appendFormat:@"%@", currentCSC];
  //[debugString appendFormat:@"%@", @"\n Expiry: "];
  //[debugString appendFormat:@"%@", currentExpiry];
  
  
  
  for(int i =0; i< currentCCN.length; i++)
  {
    if(i % 4 == 0)
    {
      [debugString appendFormat:@"%@", @" "];
    }
    [debugString appendFormat:@"%c", [currentCCN characterAtIndex:i]];
    
  }
  
  foundCardInfo = YES;
  
  if(![self luhnCheck: currentCCN])
  {
    NSLog(@"CCN Failed Luhn: REJECTED");
    return;
  }
  
  //check to make sure it doesn't show muiltiple times..
  if(!self.alertShowing)
  {
    alertShowing = YES;
    UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"OCRE Version 1.6.3"
                                                  message:debugString
                                                 delegate:self
                                        cancelButtonTitle:@"Dismiss"
                                        otherButtonTitles:nil];
    [alert show];
  }
  
  
}

- (void)alertView:(UIAlertView *)alertView clickedButtonAtIndex:(NSInteger)buttonIndex
{
  alertShowing = NO;
}

- (BOOL) luhnCheck:(NSMutableString *)ccn
{
  BOOL isOdd = YES;
  int oddSum = 0;
  int evenSum = 0;
  
  for (int i = ccn.length-1; i >= 0; i--)
  {
    int digit = [ccn characterAtIndex:i] - '0';
    if (isOdd)
    {
      oddSum += digit;
    }
    else
    {
      evenSum += digit / 5 + (2 * digit) % 10;
    }
    isOdd = !isOdd;
  }
  //zero if valid
  return ((oddSum + evenSum) % 10 == 0);
}

- (NSMutableString*) scanInfo:(NSMutableString *)destinationString :(NSString *) sourceString :(int) maxCount :(int) startIndex
{
  int count = 0;
  for (int i = startIndex; i<sourceString.length; i++)
  {
    char character = [sourceString characterAtIndex:(i)];
    if([self isNumber:(character)]|| character == '/')
    {
      [destinationString appendFormat:@"%c", character];
      count++;
    }
    if(count >= maxCount)
    {
      NSLog(@"Scan Segment Overflow");
      break;
    }
  }
  return destinationString;
}

- (bool) isNumber:(char)character
{
  if((int)character >= (int)'0' && (int)character <= (int)'9')
  {
    return YES;
  }
  return NO;
}

- (UIImage*)rotateUIImage:(UIImage*)sourceImage clockwise:(BOOL)clockwise
{
  CGSize size = sourceImage.size;
  UIGraphicsBeginImageContext(CGSizeMake(size.height, size.width));
  [[UIImage imageWithCGImage:[sourceImage CGImage] scale:1.0 orientation:clockwise ? UIImageOrientationRight : UIImageOrientationLeft] drawInRect:CGRectMake(0,0,size.height ,size.width)];
  UIImage* rotatedImage = UIGraphicsGetImageFromCurrentImageContext();
  UIGraphicsEndImageContext();
  return rotatedImage;
}

//Hack to turn off torch because card.io keeps turning it on...

-(void) setTorchOn: (bool) on
{
  Class captureDeviceClass = NSClassFromString(@"AVCaptureDevice");
  if (captureDeviceClass != nil)
  {
    AVCaptureDevice *device = [AVCaptureDevice defaultDeviceWithMediaType:AVMediaTypeVideo];
    if ([device hasTorch] && [device hasFlash])
    {
      
      [device lockForConfiguration:nil];
      if (on)
      {
        [device setTorchMode:AVCaptureTorchModeOn];
        [device setFlashMode:AVCaptureFlashModeOn];
      } else
      {
        [device setTorchMode:AVCaptureTorchModeOff];
        [device setFlashMode:AVCaptureFlashModeOff];
      }
      [device unlockForConfiguration];
    }
  }
}
/**
 *  This function is part of Tesseract's delegate. It will be called
 *  periodically as the recognition happens so you can observe the progress.
 *
 *  @param tesseract The `G8Tesseract` object performing the recognition.
 */
- (void)progressImageRecognitionForTesseract:(G8Tesseract *)tesseract {
  NSLog(@"progress: %lu", (unsigned long)tesseract.progress);
}

/**
 *  This function is part of Tesseract's delegate. It will be called
 *  periodically as the recognition happens so you can cancel the recogntion
 *  prematurely if necessary.
 *
 *  @param tesseract The `G8Tesseract` object performing the recognition.
 *
 *  @return Whether or not to cancel the recognition.
 */
- (BOOL)shouldCancelImageRecognitionForTesseract:(G8Tesseract *)tesseract {
  return NO;  // return YES, if you need to cancel recognition prematurely
}

@end