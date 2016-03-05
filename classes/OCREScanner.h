//
//  OCREScanner.h
//  OCRE
//
//  Created by Kevin M Free on 12/10/15.
//  Copyright © 2015 CTB Consulting. All rights reserved.
//

#ifndef OCREScanner_h
#define OCREScanner_h
#import <Foundation/Foundation.h>
#import <TesseractOCR/TesseractOCR.h>
#import "ScanType.h"

@interface OCREScanner : NSObject

@property (nonatomic, strong) NSOperationQueue *operationQueue;
@property (nonatomic, assign) BOOL currentOperationsCancelled;
@property (nonatomic, assign) BOOL foundCardInfo;
@property (nonatomic, assign) BOOL alertShowing;


- (id)initScanner;
- (bool) isNumber:(char)character;
- (void) addScanOperation: (UIImage *)image :(CGRect) scanAreaRect : (ScanType) scanType : (NSMutableString *) currentCCN : (NSMutableString *) currentCSC : (NSMutableString *) currentExpiry : (BOOL) isFinalOperation;
- (NSMutableString*) scanInfo:(NSMutableString *)destinationString :(NSString *) sourceString :(int) maxCount :(int) startIndex;
-(void)recognizeImageWithTesseract:(UIImage *)image;
-(void) finishedScanning : (NSMutableString *) currentCCN  : (NSMutableString *) currentCSC  : (NSMutableString *) currentExpiry;
-(BOOL) luhnCheck:(NSMutableString *)ccn;
-(void) setTorchOn: (bool) on;
- (UIImage*)rotateUIImage:(UIImage*)sourceImage clockwise:(BOOL)clockwise;


@end // end interface

#endif
