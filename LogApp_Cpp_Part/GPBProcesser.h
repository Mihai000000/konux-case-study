#pragma once
#include "log.pb.h"
#include <google/protobuf/repeated_field.h>

using namespace company;

class GPBProcesser
{
private:
    signed char *byteArray;

public:
    GPBProcesser();
    ~GPBProcesser();
    void vSetByteArray(signed char *recvByteArray);
    void vProcessGPBInput();
    void appendEventsToFile(Events events);
};

