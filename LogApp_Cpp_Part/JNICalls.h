#include <jni.h>
/* Header for class HelloJNI */

#ifndef _Included_HelloJNI
#define _Included_HelloJNI
#ifdef __cplusplus
extern "C" {
#endif
    /*
    * Class:     GPBProcesser
    * Method:    passMessagesToCpp
    * Signature: ()V
    */
    JNIEXPORT void JNICALL Java_com_company_GPBProcesser_passMessagesToCpp(JNIEnv *, jobject, jbyteArray);
    /*
    * Class:     GPBProcesser
    * Method:    startListening
    * Signature: ()V
    */
    JNIEXPORT void JNICALL Java_com_company_GPBProcesser_startListening(JNIEnv *, jobject);

#ifdef __cplusplus
}
#endif
#endif