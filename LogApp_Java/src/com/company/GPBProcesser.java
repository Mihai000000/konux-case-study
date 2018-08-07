package com.company;

import java.util.List;

public class GPBProcesser {

    static {
        System.loadLibrary("LogApp_Cpp_Part");
        startListening();

    }

    private native void passMessagesToCpp(byte[] bytes);
    private static native void startListening();

    private LogEvents.Events events;

    public boolean convertJSONToGPB(List<UserObject> userObjects)
    {
        LogEvents.Events.Event.Builder event = LogEvents.Events.Event.newBuilder();
        LogEvents.Events.Builder eventsBuilder = LogEvents.Events.newBuilder();

        int i =0;
        for (UserObject userObject : userObjects
                ) {
           event.setTimestamp(userObject.getTimestamp());
           event.setUserId(userObject.getUserId());
           event.setEvent(userObject.getMessage());

            eventsBuilder.addEvents(event);
            eventsBuilder.setCount(++i);
        }
        events = eventsBuilder.build();

        return true;
    }

    public void sendEventsToCpp()
    {
        if(events != null && events.hasCount() && events.isInitialized())
        {
            byte[] bytes = events.toByteArray();
            passMessagesToCpp(events.toByteArray());
        }
    }
}
