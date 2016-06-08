package io.github.cyning.mobilenews.base;

/**
 * @author Cyning
 * @since 2016.03.03
 * Time    6:26 PM
 * Desc    <p>类/接口描述</p>
 */
public class Load {

    private int stateSpec;



    private static final int MODE_SHIFT = 3;
    private static final int MODE_MASK  = 0x3 << MODE_SHIFT;


    public static final int INIT = 1;


    public static final int UPDATE = 2;



    public static final int LOADMORE = 3;


    public static final int SUCCESS = 1<< MODE_SHIFT;


    public static final int FAIL = 0<< MODE_SHIFT;

    public Load(int state,int deal) {
        stateSpec = state  + deal;
    }

    public int getState(){
       return stateSpec & MODE_MASK;
    }

    public int getDeal(){
       return stateSpec & ~MODE_MASK;
    }

    public boolean isSuccess(){
        return getState() == SUCCESS;
    }




}
