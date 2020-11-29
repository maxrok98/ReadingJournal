package com.example.readingjournal.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.readingjournal.database.BooksDatabase
import com.example.readingjournal.repository.BookRepository
import retrofit2.HttpException

class RefreshWorker(appContext: Context, params: WorkerParameters): CoroutineWorker(appContext, params) {
    companion object {
        const val WORK_NAME = "RefreshDataWorker"
    }
    override suspend fun doWork(): Result {
        val database = BooksDatabase.getInstance(applicationContext)
        val repository = BookRepository(database)
        return try {
            repository.refreshBook()
            Result.success()
        } catch (e: HttpException) {
            Result.retry()
        }
    }

}
