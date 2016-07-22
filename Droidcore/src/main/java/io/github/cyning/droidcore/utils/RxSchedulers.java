package io.github.cyning.droidcore.utils;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author From 小邓子（http://www.jianshu.com/users/df40282480b4/）
 * @since 2016.04.13
 * Time    10:45
 * Desc    <p>切换线程，麻麻再也不用担心我的学习 </p>
 */
public class RxSchedulers {
  private static final Observable.Transformer computationTransformer =
      new Observable.Transformer() {
        @Override public Object call(Object observable) {
          return ((Observable) observable).subscribeOn(Schedulers.computation())
              .observeOn(AndroidSchedulers.mainThread());
        }
      };
  private static final Observable.Transformer ioTransformer = new Observable.Transformer() {
    @Override public Object call(Object observable) {
      return ((Observable) observable).subscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread());
    }
  };
  private static final Observable.Transformer newTransformer = new Observable.Transformer() {
    @Override public Object call(Object observable) {
      return ((Observable) observable).subscribeOn(Schedulers.newThread())
          .observeOn(AndroidSchedulers.mainThread());
    }
  };
  private static final Observable.Transformer trampolineTransformer = new Observable.Transformer() {
    @Override public Object call(Object observable) {
      return ((Observable) observable).subscribeOn(Schedulers.trampoline())
          .observeOn(AndroidSchedulers.mainThread());
    }
  };

  /**  Don't break the chain: use RxJava's compose() operator***/

  /**
   * 切换到计算线程上，不要把IO操作放到这个上面来
   * @param <T>
   * @return
   */
  public static <T> Observable.Transformer<T, T> computation() {
    return (Observable.Transformer<T, T>) computationTransformer;
  }

  /**
   * 切换到IO线程上，异步请求或者读取文件/数据库可以
   * @param <T>
   * @return
   */
  public static <T> Observable.Transformer<T, T> io() {
    return (Observable.Transformer<T, T>) ioTransformer;
  }

  /**
   *   * 切换到新的线程上，相当于new Thread
   * @param <T>
   * @return
   */
  public static <T> Observable.Transformer<T, T> newThread() {
    return (Observable.Transformer<T, T>) newTransformer;
  }
  public static <T> Observable.Transformer<T, T> trampoline() {
    return (Observable.Transformer<T, T>) trampolineTransformer;
  }

}
