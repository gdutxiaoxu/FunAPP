package com.xujun.commonlibrary.common.mvp;

import org.simple.eventbus.EventBus;

import java.lang.ref.WeakReference;

/**
 * @ explain:
 * @ author：xujun on 2016-7-27 11:41
 * @ email：gdutxiaoxu@163.com
 */
public interface DefaultContract {

   interface View<T> extends BaseView{
       public void showProgress();

       public void hideProgress();

       public  void onSuccess(T t);

       public  void onError(Throwable throwable);

       public  void onLocal(T t);
   }


    public static class DefaultPresenter<V extends BaseView> implements BasePresenter {

        private final WeakReference<V> mBaseViewWeakReference;

        @Override
        public void start() {
            EventBus.getDefault().register(this);
        }

        @Override
        public void stop() {
            EventBus.getDefault().unregister(this);
            mBaseViewWeakReference.clear();

        }

        public  DefaultPresenter(V view){
            mBaseViewWeakReference =  new WeakReference<>(view);
        }

        public V getView(){
            return mBaseViewWeakReference.get();
        }
    }

}
