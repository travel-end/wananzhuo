package com.lol.wanandroidwithkotlin.ui.fragment.home

import androidx.lifecycle.MutableLiveData
import com.lol.wanandroidwithkotlin.base.BaseViewModel
import com.lol.wanandroidwithkotlin.common.CollectRepository
import com.lol.wanandroidwithkotlin.common.loadmore.LoadMoreStatus
import com.lol.wanandroidwithkotlin.ext.loge
import com.lol.wanandroidwithkotlin.model.bean.Article
import com.lol.wanandroidwithkotlin.util.bus.Bus
import com.lol.wanandroidwithkotlin.util.bus.USER_COLLECT_UPDATED

/**
 * Create by Jingui on 2020/6/2
 * Describe:
 * email: m15008497107@163.com
 */
class PopularViewModel:BaseViewModel() {
    companion object {
        const val INITIAL_PAGE = 0
    }

    /**
     * 关于lateinit和 lazy
     * lateinit只适用于变量var，而lazy只用于常量val
     * lazy应用于单例模式，而且当且仅当变量被第一次调用的时候，委托方法才会执行
     */
    private val popularRepository by lazy { PopularRepository() }
    private val collectRepository by lazy { CollectRepository() }

    val refreshStatus = MutableLiveData<Boolean>()
    val reloadStatus = MutableLiveData<Boolean>()

    val articleList:MutableLiveData<MutableList<Article>> = MutableLiveData()
    val loadMoreStatus = MutableLiveData<LoadMoreStatus>()

    private var page = INITIAL_PAGE

    fun refreshArticleList() {
        refreshStatus.value = true
        reloadStatus.value = false
        // launch:最常用的用于启动协程的方式，最终返回一个Job类型的对象，这个Job类型对象实际上
        // 是一个接口，包含了许多我们常用的方法。例如join()启动一个协程，cancel()取消一个协程
        // 注：这种方式启动的协程任务是不会阻塞线程的
        launch(

            // 多个任务并行  async和await一般是成对出现的
            // async用于启动一个异步的协程任务，await用于去得到协程任务结束时返回的结果，结果通过一个Deferred对象返回的
            block = {
                val topArticleListDefferd = async {
                    popularRepository.getTopArticleList()
                }

                val paginationDefferd = async {
                    popularRepository.getArticleList(INITIAL_PAGE)
                }

                val topArticleList = topArticleListDefferd.await()
                    .apply {
                        forEach {
                            it.top = true
                        }
                    }
                val pagination = paginationDefferd.await()
//                loge("$pagination")

                page = pagination.curPage
                articleList.value = mutableListOf<Article>().apply {
                    addAll(topArticleList)
                    addAll(pagination.datas)
                }
                refreshStatus.value =false
            },
            error = {
                refreshStatus.value = false
                reloadStatus.value = page == INITIAL_PAGE
            }
        )
    }

    fun loadMoreArticleList() {
        loadMoreStatus.value = LoadMoreStatus.LOADING
        launch(
            block = {
                val pagination = popularRepository.getArticleList(page)
                page = pagination.curPage
                val currentList = articleList.value ?: mutableListOf()
                currentList.addAll(pagination.datas)
                articleList.value = currentList
                loadMoreStatus.value = if (pagination.offset >= pagination.total) {
                    LoadMoreStatus.END
                } else {
                    LoadMoreStatus.COMPLETED
                }
            },
            error = {
                loadMoreStatus.value = LoadMoreStatus.ERROR
            }
        )
    }

    /**
     * id:文章id
     */
    fun collect(id: Int) {
        launch(
            block = {
                collectRepository.collect(id)
                userRepository.updateUserInfo(userRepository.getUserInfo()!!.apply {
                    if (!collectIds.contains(id)) collectIds.add(id)
                })
                updateItemCollectState(id to true)
                Bus.post(USER_COLLECT_UPDATED, id to true)
            },
            error = {
                updateItemCollectState(id to false)
            }
        )
    }
    fun uncollect(id: Int) {
        launch(
            block = {
                collectRepository.uncollect(id)
                userRepository.updateUserInfo(userRepository.getUserInfo()!!.apply {
                    if (collectIds.contains(id)) collectIds.remove(id)
                })
                updateItemCollectState(id to false)
                Bus.post(USER_COLLECT_UPDATED, id to false)
            },
            error = {
                updateItemCollectState(id to true)
            }
        )
    }
    /**
     * 更新列表收藏状态
     */
    fun updateListCollectState() {
        val list = articleList.value
        if (list.isNullOrEmpty()) return
        if (userRepository.isLogin()) {
            val collectIds = userRepository.getUserInfo()?.collectIds ?: return
            list.forEach { it.collect = collectIds.contains(it.id) }
        } else {
            list.forEach { it.collect = false }
        }
        articleList.value = list
    }

    /**
     * 更新Item的收藏状态
     */
    fun updateItemCollectState(target: Pair<Int, Boolean>) {
        val list = articleList.value
        val item = list?.find { it.id == target.first } ?: return
        item.collect = target.second
        articleList.value = list
    }
}