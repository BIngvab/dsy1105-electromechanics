package com.dominguezdiaz.electrotrack.data
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase


val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL(
            """
            CREATE TABLE IF NOT EXISTS `trabajos` (
                `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                `titulo` TEXT NOT NULL,
                `descripcion` TEXT NOT NULL,
                `trabajador` TEXT NOT NULL,
                `estado` TEXT NOT NULL
                
            )
            """.trimIndent()

        )
    }
}


val MIGRATION_3_4 = object : Migration(3, 4) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL(
            """
            ALTER TABLE `informes`
            ADD COLUMN `rutaFotografia` TEXT DEFAULT NULL
            """.trimIndent()

        )

    }

}


val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL(
            """
            CREATE TABLE IF NOT EXISTS `informes` (
                `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                `trabajoId` INTEGER NOT NULL,
                `descripcion` TEXT NOT NULL,
                `observaciones` TEXT NOT NULL,
                `fechaHora` TEXT NOT NULL
            )
            """.trimIndent()
        )

    }

}

