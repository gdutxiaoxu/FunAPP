package com.xujun.funapp.view.main.fragment.home;

import com.xujun.funapp.beans.YiYuanNewsClassify.ShowapiResBodyEntity.ChannelListEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xujun  on 2016/12/27.
 * @email gdutxiaoxu@163.com
 */

public class Info {



    public List<ChannelListEntity> getData() {
        ArrayList<ChannelListEntity> mList = new ArrayList<>();
        mList.add(new ChannelListEntity("5572a108b3cdc86cf39001d1", "互联网焦点"));
        mList.add(new ChannelListEntity("5572a108b3cdc86cf39001d4", "体育焦点"));
        mList.add(new ChannelListEntity("5572a108b3cdc86cf39001d7", "教育焦点"));
        return mList;
    }


}
