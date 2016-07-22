package io.github.cyning.droidcore.utils;

import java.util.HashMap;
import java.util.Map;

import rx.subscriptions.CompositeSubscription;

/**
 * @author Cyning
 * @since 2016.03.08
 * Time    4:16 PM
 * Desc    <p>类/接口描述</p>
 */

public class RxAppService {

    private static final RxAppService RXAPPSERVICE=new RxAppService();

    private Map<Integer,CompositeSubscription> mCompositeSubByTaskId;

    private RxAppService(){
        mCompositeSubByTaskId = new HashMap<Integer,CompositeSubscription>();
    }
    public static RxAppService getInstance(){
        return RXAPPSERVICE;
    }

    public void addCompositeSub(int taskId) {
        CompositeSubscription compositeSubscription;
        if(mCompositeSubByTaskId.get(taskId)==null) {
            compositeSubscription = new CompositeSubscription();
            mCompositeSubByTaskId.put(taskId, compositeSubscription);
        }
    }

    public void removeCompositeSub(int taskId) {
        CompositeSubscription compositeSubscription;
        if(mCompositeSubByTaskId!=null&& mCompositeSubByTaskId.get(taskId)!=null){
            compositeSubscription= mCompositeSubByTaskId.get(taskId);
            compositeSubscription.unsubscribe();
            mCompositeSubByTaskId.remove(taskId);
        }
    }

    public CompositeSubscription getCompositeSubscription(int taskId) {
        CompositeSubscription compositeSubscription ;
        if(mCompositeSubByTaskId.get(taskId)==null) {
            compositeSubscription = new CompositeSubscription();
            mCompositeSubByTaskId.put(taskId, compositeSubscription);
        }else {
            compositeSubscription= mCompositeSubByTaskId.get(taskId);
        }
        return compositeSubscription;
    }

}
