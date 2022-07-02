package com.muscletracking.mtapi.dao

import com.muscletracking.mtapi.dao.user.UserDao
import com.muscletracking.mtapi.entity.user.UserEntity
import com.ninja_squad.dbsetup.DbSetup
import com.ninja_squad.dbsetup.Operations.deleteAllFrom
import com.ninja_squad.dbsetup.Operations.sequenceOf
import com.ninja_squad.dbsetup.bind.DefaultBinderConfiguration
import com.ninja_squad.dbsetup.destination.DataSourceDestination
import com.ninja_squad.dbsetup.destination.Destination
import com.ninja_squad.dbsetup.operation.DeleteAll
import com.ninja_squad.dbsetup_kotlin.dbSetup
import com.ninja_squad.dbsetup_kotlin.insertInto
import org.amshove.kluent.`should be equal to`
import org.amshove.kluent.shouldNotBeNull
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import java.sql.Connection
import java.time.LocalDateTime
import javax.sql.DataSource

/**
 * Dbsetupを利用したテストを行う
 * Dbsetupはテスト前にテストテーブルを全削除・テストデータのインサート・テストの順で実行する
 * 原則としてテスト終了後、データの削除は行わない
 * test schemaを利用してテストを行う
 */
@ActiveProfiles("test")
@SpringBootTest
internal class UserDaoTest {

    @Autowired
    lateinit var dataSource: DataSource

    @Autowired
    lateinit var userDao: UserDao

    // dbsetup設定
    lateinit var destination: Destination

    lateinit var deleteAllUserTable: DeleteAll

    @BeforeEach
    fun setUp() {
        destination = DataSourceDestination(dataSource)
        deleteAllUserTable = deleteAllFrom("m_user")
    }

    @AfterEach
    fun tearDown() {
    }

    @Test
    @DisplayName("ユーザーIDでユーザーを1件取得できる")
    fun selectByIdTest() {
        // db test data setup
        val insertTestUser = insertInto("m_user") {
            withDefaultValue("regdate", LocalDateTime.now())
            withDefaultValue("upddate", LocalDateTime.now())
            withDefaultValue("version", 1)
            columns("userid", "username", "password", "regid", "updid")
            values("test1", "テストユーザー", "test1", "test1", "test1")
        }

        val operation = sequenceOf(deleteAllUserTable, insertTestUser)
        DbSetup(destination, operation).launch()

        // dbselect
        val actual = userDao.selectById("test1")

        // assertion
        actual.shouldNotBeNull()
        actual.userId `should be equal to` "test1"
        actual.userName `should be equal to` "テストユーザー"
        actual.password `should be equal to` "test1"
        actual.regId `should be equal to` "test1"
        actual.regId `should be equal to` "test1"
    }

    @Test
    @DisplayName("新規ユーザー1件登録できる")
    fun insertNewUserTest() {
        val operation = sequenceOf(deleteAllUserTable)
        DbSetup(destination, operation).launch()

        val newUser =
            UserEntity(userId = "test1", userName = "テストユーザー", password = "test1", regId = "test1", updId = "test1")
        val insert = userDao.insertNewUser(newUser)

        insert.entity.userId.`should be equal to`("test1")
        insert.entity.userName.`should be equal to`("テストユーザー")
        insert.entity.password.`should be equal to`("test1")
    }

    @Test
    @DisplayName("ユーザー情報を更新できる")
    fun updateUserTest() {
        // db test data setup
        val insertTestUser = insertInto("m_user") {
            withDefaultValue("regdate", LocalDateTime.now())
            withDefaultValue("upddate", LocalDateTime.now())
            withDefaultValue("version", 1)
            columns("userid", "username", "password", "regid", "updid")
            values("test1", "テストユーザー", "test1", "test1", "test1")
        }

        val operation = sequenceOf(deleteAllUserTable, insertTestUser)
        DbSetup(destination, operation).launch()

        val updateUser =
            UserEntity(userId = "test1", userName = "テストユーザー2", password = "updated", regId = "test1", updId = "test1")
        val update = userDao.updateUser(updateUser)

        update.entity.userName.`should be equal to`("テストユーザー2")
        update.entity.password.`should be equal to`("updated")

    }
}