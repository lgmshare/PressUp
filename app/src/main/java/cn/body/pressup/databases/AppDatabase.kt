package cn.body.pressup.databases

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import cn.body.pressup.App
import cn.body.pressup.models.BloodPress

@Database(entities = [BloodPress::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    companion object {

        private const val DB = "press.db"

        const val T_BP = "t_bp"

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(): AppDatabase {
            if (INSTANCE != null) return INSTANCE!!

            synchronized(AppDatabase::class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(App.getInstance(), AppDatabase::class.java, DB)
                        .fallbackToDestructiveMigration()
                        .allowMainThreadQueries()
                        .addCallback(object : Callback() {
                            override fun onCreate(db: SupportSQLiteDatabase) {
                                super.onCreate(db)
                            }
                        })
                        .build()
                }
            }
            return INSTANCE!!
        }
    }

    abstract fun pressDataDao(): BloodPressDataDao

}
