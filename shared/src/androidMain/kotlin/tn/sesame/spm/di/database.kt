package tn.sesame.spm.di

import app.cash.sqldelight.ColumnAdapter
import app.cash.sqldelight.async.coroutines.synchronous
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import org.koin.core.module.Module
import org.koin.dsl.module
import tn.sesame.spmdatabase.SesameWorksLifeDatabase

actual val databaseModule : Module = module {
    single<SqlDriver> {
        AndroidSqliteDriver(SesameWorksLifeDatabase.Schema.synchronous(), get(), DATABASE_FILE_NAME)
    }
    single<ColumnAdapter<Long,ULong >> {
        object :ColumnAdapter<Long, ULong>{
            override fun decode(databaseValue: ULong): Long = databaseValue.toLong()

            override fun encode(value: Long): ULong = value.toULong()
        }
    }
    single<SesameWorksLifeDatabase>{
        SesameWorksLifeDatabase(
            get<SqlDriver>()
        )
    }
}