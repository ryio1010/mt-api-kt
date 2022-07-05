package com.muscletracking.mtapi.dao

import com.muscletracking.mtapi.dao.user.UserDao
import com.muscletracking.mtapi.entity.user.UserEntity
import com.ninja_squad.dbsetup.DbSetup
import com.ninja_squad.dbsetup.Operations.deleteAllFrom
import com.ninja_squad.dbsetup.Operations.sequenceOf
import com.ninja_squad.dbsetup.destination.DataSourceDestination
import com.ninja_squad.dbsetup.destination.Destination
import com.ninja_squad.dbsetup.operation.DeleteAll
import com.ninja_squad.dbsetup_kotlin.insertInto
import junit.framework.TestCase.assertTrue
import org.amshove.kluent.`should be equal to`
import org.amshove.kluent.shouldNotBeNull
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.test.context.TestPropertySource
import org.testcontainers.containers.PostgisContainerProvider
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.containers.PostgreSQLContainerProvider
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.utility.DockerImageName
import java.time.LocalDateTime
import javax.sql.DataSource

/**
 * DbSetupを利用したテストを行う
 * DbSetupは原則としてテーブルを全削除・データのインサート・テストの順で実行する
 * 原則としてテスト終了後、データの削除は行わない
 * TestContainersを利用してテストを行う
 */
@Testcontainers
@SpringBootTest
@ActiveProfiles("test")
internal class UserDaoTest {

    companion object {
        @Container
        @JvmStatic
        val postgres = PostgreSQLContainer(DockerImageName.parse("postgres:12.6-alpine")).apply {
            withDatabaseName("testdb")
            withUsername("testuser")
            withPassword("testuser")
            withInitScript("schema.sql")
        }

        @DynamicPropertySource
        @JvmStatic
        fun setup(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url", postgres::getJdbcUrl)
        }
    }

    @Autowired
    lateinit var dataSource: DataSource

    @Autowired
    lateinit var userDao: UserDao

    // DBSetup設定
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
    fun test() {
        assertTrue(postgres.isRunning)
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