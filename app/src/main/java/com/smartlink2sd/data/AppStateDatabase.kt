package com.smartlink2sd.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class AppStateDatabase(c: Context): SQLiteOpenHelper(c,"smartlink2sd.db",null,1) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("""CREATE TABLE app_state(
            package_name TEXT PRIMARY KEY, version_code INTEGER NOT NULL DEFAULT 0,
            uid INTEGER NOT NULL DEFAULT -1, storage_location TEXT NOT NULL DEFAULT 'INTERNAL',
            link_status TEXT NOT NULL DEFAULT 'NOT_LINKED', linked_components TEXT NOT NULL DEFAULT '',
            destination TEXT NOT NULL DEFAULT '', mount_type TEXT NOT NULL DEFAULT '',
            freeze_status TEXT NOT NULL DEFAULT 'ACTIVE', backend TEXT NOT NULL DEFAULT 'ANDROID',
            backup_metadata TEXT NOT NULL DEFAULT '', last_verification INTEGER NOT NULL DEFAULT 0
        )""")
    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {}
}
