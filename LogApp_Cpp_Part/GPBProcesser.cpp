#include "GPBProcesser.h"
#include <iostream>
#include <string>
#include <istream> 
#include <ostream> 
#include <fstream>
#include <sstream> 

using namespace std;

GPBProcesser::GPBProcesser() : byteArray(nullptr)
{
}


GPBProcesser::~GPBProcesser()
{
}

void GPBProcesser::vSetByteArray(signed char *recvByteArray)
{
    byteArray = recvByteArray;
}

void GPBProcesser::vProcessGPBInput()
{
    if (byteArray)
    {
        int c = 0;
        std::ostringstream out(std::ios::binary);
        while (byteArray[c])
        {
            out << byteArray[c];
            ++c;
        }
        if (out.good())
        {
            std::istringstream in(out.str(), std::ios::binary);
            Events events;
            events.ParseFromArray(byteArray, c);

            appendEventsToFile(events);         
            
        }
        

    }
}

void GPBProcesser::appendEventsToFile(Events events)
{
    std::ofstream outfile;

    outfile.open("test.txt", std::ios_base::app);    
    const ::google::protobuf::RepeatedPtrField< ::company::Events_Event > repeated = events.events();
    for (int i = 0; i < events.count(); ++i)
    {
        Events_Event event = repeated.Get(i);
        outfile << event.timestamp() << " " << event.userid() << ";" << event.event() << endl;
    }
}