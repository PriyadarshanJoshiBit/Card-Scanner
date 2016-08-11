





#ifndef OCRE
#define OCRE

#include "tesseract/baseapi.h"
#include "leptonica/allheaders.h"
#include "frame.h"

void  ocre_init();
void  ocre_scanImage( IplImage *croppedImage, int digits = 16 );
bool  ocre_complete();
char* ocre_result();
void ocre_reset();

#endif