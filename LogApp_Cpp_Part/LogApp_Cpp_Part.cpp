// LogApp_Cpp_Part.cpp : Defines the exported functions for the DLL application.
//


#include "JNICalls.h"
#include "log.pb.h"
#include "GPBProcesser.h"
#include <iostream>
#include <process.h>
#include <Windows.h>
using namespace std;

static bool process;
static GPBProcesser gpbProcesser;

unsigned __stdcall loop(void *data)
{
    while (true)
    {
        if (process)
        {
            process = false;
            //start processing
            gpbProcesser.vProcessGPBInput();
        }
    }
    return 0;
}

// Implementation of native method passMessagesToCpp() of GPBProcesser class
JNIEXPORT void JNICALL Java_com_company_GPBProcesser_passMessagesToCpp(JNIEnv *env, jobject thisObj, jbyteArray jByteArray) {
    
    jbyte *native_array; 
    int i = 0;
    native_array = env->GetByteArrayElements(jByteArray, NULL); 
    if (!native_array)
    {
        cout << "Transmission not completed. Please send again." << endl;
        return;
    }

    process = true;
    gpbProcesser.vSetByteArray(native_array);

    return;
}

// Implementation of native method startListening() of GPBProcesser class
JNIEXPORT void JNICALL Java_com_company_GPBProcesser_startListening(JNIEnv *env, jobject thisObj) {
    unsigned int threadID;
    HANDLE hThread = (HANDLE)_beginthreadex(NULL, 0, &loop, NULL, 0, &threadID);
    
    return;
}