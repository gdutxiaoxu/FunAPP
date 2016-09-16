package com.xujun.funapp.common.mvp;

import java.lang.ref.WeakReference;

import de.greenrobot.event.EventBus;

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


    public static class DefaultPresenter<T> implements BasePresenter {

        private final WeakReference<T> mBaseViewWeakReference;

        @Override
        public void start() {
            EventBus.getDefault().register(this);
        }

        @Override
        public void stop() {
            EventBus.getDefault().unregister(this);
            mBaseViewWeakReference.clear();

        }

        public  DefaultPresenter(T view){
            mBaseViewWeakReference =  new WeakReference<>(view);
        }

        public T getView(){
            return mBaseViewWeakReference.get();
        }
    }

}
