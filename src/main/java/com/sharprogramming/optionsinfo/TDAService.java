package com.sharprogramming.optionsinfo;

import com.studerw.tda.model.option.OptionChain;

import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class TDAService {

    SharprogrammingTDAClient client;

    public TDAService(){
        authenticate();
    }
    public void authenticate(){
        Properties props = new Properties();
        props.setProperty("tda.client_id","LNTBSLP7G1G9QTTWPNHIKBYOHMYFAOXQ");
        props.setProperty("tda.token.refresh", "ytm+cOi9f9uXgXJqIkBALT9InDRQiblAovzNlvDglc5G5AUeO6d2q9kGZ+59dgwtigidmqRO7R7TKDyLbtj9NAiDtkWSBWij/bf5w/kabs0Xhhoxuz7yCP/GQ9MOxm/cjJD0ilaDXsprO1+wf4lvyPx0wwgeg8EoI30KMTVEgkk2+GatA3dVuDbagM9TzPoLsuOW6WQIk8KnwlE89iRCEnoAAIk9UENsCJjzAy7mNAvCBcbT8YHq7lNLdHLBIWd3WBBZ7EpTeesVlEET2/q7krQF18SU78Ig3jZ0PRubOOg/n6k7RirAs4aFunzYEiRmEfhVdQtfxoa53riVCoycktS68+0+FpNjivd3dqCMLi16xjd8E1YXI5yUycocpuZ5+JNqad3V7APeAvmJKHbLWuHI6HOGOOZMTGMY2QmXiK3us0iVJNfnA5wCSFD100MQuG4LYrgoVi/JHHvlvpK1YlnjMQATaQ/HAo8PMGEz5IGKbIDYa7q1FIMSTZiOjdMPrJzUjikwqRBicUuEC4P95zKrcTNjly0EA7r9qxG0ywK/2wEzGDZvD6o34Q1tPJgcETZhUuB6ewnXPQWRKjBFOUD50KKMWU+HI1717xbRBb1EFfR+kCkMmKhyjPZ6ODWBAR88He7M/oD0RrEbDh11rWImJJT+6D2aTK4MPKHhm3g+Ge3KT9s6o6T4xM+bYrgglFoamx7DWXW7wZAiPa0NOrcMKpNwnxguwg+tW5PmRHvVZ/Uz7s4zI7cG/GGIsSH15671t/3sxde6GB+I6UiA0CV5ltF4nZG8OCMME3oBMRINYWbjha1J0OHIx99/OPskZWyVNB6lzJ3aWAmNsub1wqVDqaD4L0dJd5WPl99836YTOQWZxzuMAOGhlAyxjz1gVwWei4XY1LI=212FD3x19z9sWBHDJACbC00B75E");

        client = new SharprogrammingTDAClient(props);
    }

    public void fetchOptionChain(String symbol, Consumer<OptionChain> completionHandler){
        Executors.newSingleThreadExecutor().execute(() -> completionHandler.accept(client.getOptionChain(symbol)));
    }

}
