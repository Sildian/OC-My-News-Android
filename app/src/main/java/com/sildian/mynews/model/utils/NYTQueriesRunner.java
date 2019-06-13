package com.sildian.mynews.model.utils;


import com.sildian.mynews.model.TopStoriesAPIResponse;

import java.lang.ref.WeakReference;

import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

/*************************************************************************************************
 * NYTQueriesRunner
 * This class runs queries to the New York Times API
 ************************************************************************************************/

public class NYTQueriesRunner {

    /**The interface providing the responses to the listener**/

    public interface NYTQueryResponseListener{
        void onResponse(TopStoriesAPIResponse topStoriesAPIResponse);
        void onError();
    }

    /**Attributes**/

    private WeakReference<NYTQueryResponseListener> listener;       //The listener which listen the response
    private Disposable disposable;                                  //The disposable which gets the response

    /**Constructor**/

    public NYTQueriesRunner(NYTQueryResponseListener listener) {
        this.listener=new WeakReference<NYTQueryResponseListener>(listener);
    }

    /**Runs the query to get the articles
     * @param section : the section name
     */

    public void runTopStoriesArticlesRequest(String section){
        this.disposable= NYTStreams.streamGetTopStoriesArticles(section).subscribeWith(new DisposableObserver<TopStoriesAPIResponse>(){
            @Override
            public void onNext(TopStoriesAPIResponse topStoriesAPIResponse) {
                sendOnResponseToListener(topStoriesAPIResponse);
            }

            @Override
            public void onError(Throwable e) {
                sendOnErrorToListener();
            }

            @Override
            public void onComplete() {
                disposeDisposable();
            }
        });
    }

    /**Disposes with the disposable**/

    private void disposeDisposable(){
        if (this.disposable != null && !this.disposable.isDisposed()){
            this.disposable.dispose();
        }
    }

    /**Sends the response to the listener**/

    private void sendOnResponseToListener(TopStoriesAPIResponse topStoriesAPIResponse){
        this.listener.get().onResponse(topStoriesAPIResponse);
    }

    /**Sends an error to the listener**/

    private void sendOnErrorToListener(){
        this.listener.get().onError();
    }
}
