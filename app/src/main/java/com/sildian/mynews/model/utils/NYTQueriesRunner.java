package com.sildian.mynews.model.utils;


import com.sildian.mynews.model.most_popular_api.MostPopularAPIResponse;
import com.sildian.mynews.model.top_stories_api.TopStoriesAPIResponse;

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
        void onResponse(MostPopularAPIResponse mostPopularAPIResponse);
        void onError();
    }

    /**Attributes**/

    private WeakReference<NYTQueryResponseListener> listener;       //The listener which listen the response
    private Disposable disposable;                                  //The disposable which gets the response

    /**Constructor**/

    public NYTQueriesRunner(NYTQueryResponseListener listener) {
        this.listener=new WeakReference<NYTQueryResponseListener>(listener);
    }

    /**Runs the query to get the articles from NYT top stories API
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

    /**Runs the query to get the articles from NYT Most popular API*/

    public void runMostPopularArticlesRequest(){
        this.disposable= NYTStreams.streamGetMostPopularArticles().subscribeWith(new DisposableObserver<MostPopularAPIResponse>(){
            @Override
            public void onNext(MostPopularAPIResponse mostPopularAPIResponse) {
                sendOnResponseToListener(mostPopularAPIResponse);
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

    /**Sends the responses to the listener**/

    private void sendOnResponseToListener(TopStoriesAPIResponse topStoriesAPIResponse){
        this.listener.get().onResponse(topStoriesAPIResponse);
    }

    private void sendOnResponseToListener(MostPopularAPIResponse mostPopularAPIResponse){
        this.listener.get().onResponse(mostPopularAPIResponse);
    }

    private void sendOnErrorToListener(){
        this.listener.get().onError();
    }
}
