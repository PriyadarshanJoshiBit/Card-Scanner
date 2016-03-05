//
//  CardLayout.h
//  icc
//
//  Created by Kevin M Free on 12/14/15.
//  Copyright © 2015 CTB Consulting. All rights reserved.
//

#ifndef CardLayout_h
#define CardLayout_h

#import "CardDefinition.h"


@interface CardLayouts : NSObject

@property (nonatomic, strong) NSMutableArray *CardDefinitions;

-(id)initCardLayouts;



@end

@implementation CardLayouts

-(id)initCardLayouts
{
  if(self = [super init])
  {
    
    //TODO: Add these defs to a file...
    self.CardDefinitions = [[NSMutableArray alloc] init];
    
    CardDefinition *vclpV = [[CardDefinition alloc]initCardDefinition: @"Venture Card Laser Personalization Vertical" : YES : 16 :
                             CGRectMake(20, 78, 70, 23) :CGRectMake(20, 116, 70, 23) :CGRectMake(20, 153, 70, 23) : CGRectMake(20, 192, 70, 23) :
                             CGRectMake(54,244,34,16):
                             CGRectMake(42, 220, 51, 21)];
    
    /*
     CardDefinition *vclpV = [[CardDefinition alloc]initCardDefinition: @"Venture Card Laser Personalization Vertical" : YES : 16 :
     CGRectMake(18, 74, 71, 27) :CGRectMake(21, 114, 70, 25) :CGRectMake(19, 153, 73, 23) : CGRectMake(18, 190, 76, 27) :
     CGRectMake(54,244,34,16):
     CGRectMake(42, 220, 51, 21)];*/
    
    CardDefinition *vclpH = [[CardDefinition alloc]initCardDefinition: @"Venture Card Laser Personalization Horizontal" : NO : 16 :
    CGRectMake(16, 200, 50, 20) :CGRectMake(62, 200, 50, 20) :CGRectMake(112, 200, 50, 20): CGRectMake(160, 200, 50, 20) :
    CGRectMake(0,0,0,0):
    CGRectMake(84, 241, 49, 18)];

    
    CardDefinition *qufpH = [[CardDefinition alloc]initCardDefinition: @"Quicksilver Ultragraphics flat print Horizontal" : NO : 16 :
    CGRectMake(16, 197, 50, 20) : CGRectMake(70, 197, 50, 20) :CGRectMake(122, 197, 50, 20): CGRectMake(175, 197, 50, 20) :
    CGRectMake(0, 0, 0, 0) :
    CGRectMake(242,235,45,19)];
    
    CardDefinition *cbgcbp = [[CardDefinition alloc]initCardDefinition: @"CBGC Buy Powercard" : NO : 16 :
                             CGRectMake(15, 256, 45, 20) : CGRectMake(60, 256, 45, 20) :CGRectMake(105, 256, 45, 20): CGRectMake(150, 256, 45, 20) :
                             CGRectMake(0, 0, 0, 0) :
                             CGRectMake(0,0,0,0)];
    cbgcbp.RotateLeft = YES;
    
    
    CardDefinition *lacvn = [[CardDefinition alloc]initCardDefinition: @"laCaixa Visa New" : NO : 16 :
                              CGRectMake(45, 155, 75, 25) : CGRectMake(115, 155, 75, 25) :CGRectMake(195, 155, 75, 25): CGRectMake(275, 155, 75, 25) :
                              CGRectMake(0, 0, 0, 0) :
                              CGRectMake(0,0,0,0)];
    
    CardDefinition *coapts = [[CardDefinition alloc]initCardDefinition: @"Capital One All Point 360" : NO : 16 :
                             CGRectMake(12, 174, 65, 24) : CGRectMake(75, 174, 65, 24) :CGRectMake(145, 174, 65, 24): CGRectMake(210, 174, 65, 24) :
                             CGRectMake(0, 0, 0, 0) :
                             CGRectMake(0,0,0,0)];
    
    CardDefinition *cmcwe = [[CardDefinition alloc]initCardDefinition: @"CMC World Elite" : NO : 16 :
                              CGRectMake(18, 108, 56, 20) : CGRectMake(74, 108, 56, 20) :CGRectMake(134, 174, 56, 20): CGRectMake(194, 174, 56, 20) :
                              CGRectMake(0, 0, 0, 0) :
                              CGRectMake(0,0,0,0)];


    
    
    
   
    [self.CardDefinitions addObject:vclpV];
    [self.CardDefinitions addObject:vclpH];
    [self.CardDefinitions addObject:qufpH];
    [self.CardDefinitions addObject:cbgcbp];
    [self.CardDefinitions addObject:lacvn];
    [self.CardDefinitions addObject:coapts];
    [self.CardDefinitions addObject:cmcwe];

  }
  return self;
}


@end

#endif /* CardLayout_h */
