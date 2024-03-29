package tn.sesame.spm.domain.entities

import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.Month
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime
import kotlin.test.Test
import kotlin.test.asserter

class SesameProjectTestCases {

    private val projectSuperVisor : SesameTeacher = SesameTeacher(
        registrationID = "",
        firstName = "Supervisor",
        lastName = "",
        email = "",
        sex = SesameUserSex.Male,
        profilePicture = "",
        profBackground = "Software design engineer",
        assignedClasses = listOf(SesameClass("ingta4c","ingt","4","c"))
    )
    private val noProjectCollaborators : Map<SesameStudent,SesameProjectJoinRequestState> = mapOf()
    private val projectCollaborators : Map<SesameStudent,SesameProjectJoinRequestState> = buildMap{
        repeat(5){
            put(SesameStudent(
                registrationID = "id$it",
                email = "email$it@mail.com",
                firstName = "firstname$it",
                lastName = "",
                profilePicture = "",
                portfolioId = "",
                sex = SesameUserSex.Male,
                sesameClass = SesameClass("ingta4c","ingt","4","c")
            ),(if (it <3) SesameProjectJoinRequestState.ACCEPTED else SesameProjectJoinRequestState.WAITING_APPROVAL))
        }
    }
    private val fullProjectCollaborators : Map<SesameStudent,SesameProjectJoinRequestState> = buildMap{
        repeat(5){
           put( SesameStudent(
               registrationID = "id$it",
               email = "email$it@mail.com",
               firstName = "firstname$it",
               lastName = "",
               profilePicture = "",
               portfolioId = "",
               sex = SesameUserSex.Male,
               sesameClass = SesameClass("ingta4c","ingt","4","c")
           ),SesameProjectJoinRequestState.ACCEPTED)
        }
    }
    private val sesameProject = SesameProject(
        "idp",
        ProjectType.PFE,
        "",
        projectSuperVisor,
        projectCollaborators,
        5,
        LocalDateTime(dayOfMonth = 1, month = Month.MARCH, year = 2024, hour = 23, minute = 23)..LocalDateTime(dayOfMonth = 1, month = Month.SEPTEMBER, year = 2024, hour = 23, minute = 23),
        LocalDateTime(dayOfMonth = 23, month = Month.FEBRUARY, year = 2024, hour = 8, minute = 23),
        LocalDateTime(dayOfMonth = 23, month = Month.DECEMBER, year = 2024, hour = 8, minute = 23),
        listOf(),
        listOf()
    )

    @Test
    fun `GIVEN a 5 maximum Collaborators project AND 3 Accepted collaborators THEN total collaborators should be 3`() {
        println(sesameProject.collaboratorsToJoin)
       asserter
           .assertEquals(
               actual = sesameProject.joinedCollaborators.size,
               expected = 3,
               message = "There are only 3 accepted collaborators ! rejected or pending collaborators to join are ignored !",
           )
    }

    @Test
    fun `GIVEN a 5 maximum Collaborators project AND no collaborators requested to join THEN total collaborators should be 0`() {
        val project = sesameProject
            .copy(
                collaboratorsToJoin = noProjectCollaborators
            )
       asserter
           .assertEquals(
               actual = project.joinedCollaborators.size,
               expected = 0,
               message = "There are no collaborators in the project !",
           )
    }

    @Test
    fun `GIVEN a 5 maximum Collaborators project AND 5 accepted collaborators THEN total collaborators should be 5`() {
        val project = sesameProject
            .copy(
                collaboratorsToJoin = fullProjectCollaborators
            )
        asserter
            .assertTrue(
                actual = project.isFullOfCollaborators(),
                message = "This project's collaborators have reached the max limit !",
            )
    }
    @Test
    fun `GIVEN a 5 maximum Collaborators project AND 8 mistakenly accepted collaborators THEN total collaborators should be 5`() {
        val project = sesameProject
            .copy(
                collaboratorsToJoin = fullProjectCollaborators + (SesameStudent(
                    registrationID = "id2$1",
                    email = "email2@mail.com",
                    firstName = "firstname3",
                    lastName = "",
                    profilePicture = "",
                    portfolioId = "",
                    sex = SesameUserSex.Male,
                    sesameClass = SesameClass("ingta4c","ingt","4","c")
                ) to  SesameProjectJoinRequestState.ACCEPTED)
            )
        asserter
            .assertTrue(
                actual = project.isFullOfCollaborators(),
                message = "This project's collaborators have reached the max limit !",
            )
    }

    @Test
    fun `GIVEN a project with a incoming start date THEN the project should not be active`() {
        val startDate= Instant.fromEpochMilliseconds(Clock.System.now().toEpochMilliseconds()).plus( value =1, unit =  DateTimeUnit.DAY, timeZone = TimeZone.currentSystemDefault())
            .toLocalDateTime(TimeZone.currentSystemDefault())
        val endDate = Instant.fromEpochMilliseconds(Clock.System.now().toEpochMilliseconds()).plus( value =7, unit =  DateTimeUnit.MONTH, timeZone = TimeZone.currentSystemDefault())
            .toLocalDateTime(TimeZone.currentSystemDefault())

        val project = sesameProject
            .copy(
                duration = startDate..endDate
            )
        asserter
            .assertTrue(
                actual = project.isActive().not(),
                message = "This project's start date is not reached yet ! project not active",
            )
    }

    @Test
    fun `GIVEN a project that started and still has not ended yet THEN the project should  be active`() {
        val startDate= Instant.fromEpochMilliseconds(Clock.System.now().toEpochMilliseconds()).minus( value =2, unit =  DateTimeUnit.DAY, timeZone = TimeZone.currentSystemDefault())
            .toLocalDateTime(TimeZone.currentSystemDefault())
        val endDate = Instant.fromEpochMilliseconds(Clock.System.now().toEpochMilliseconds()).plus( value =7, unit =  DateTimeUnit.MONTH, timeZone = TimeZone.currentSystemDefault())
            .toLocalDateTime(TimeZone.currentSystemDefault())
        val project = sesameProject
            .copy(
                duration = startDate..endDate
            )
        asserter
            .assertTrue(
                actual = project.isActive(),
                message = "This project has started already and is active",
            )
    }

    @Test
    fun `GIVEN a project's end date that is in the past THEN the project should  be over`() {
        val startDate= Instant.fromEpochMilliseconds(Clock.System.now().toEpochMilliseconds()).minus( value =2, unit =  DateTimeUnit.MONTH, timeZone = TimeZone.currentSystemDefault())
            .toLocalDateTime(TimeZone.currentSystemDefault())
        val endDate = Instant.fromEpochMilliseconds(Clock.System.now().toEpochMilliseconds()).minus( value =7, unit =  DateTimeUnit.DAY, timeZone = TimeZone.currentSystemDefault())
            .toLocalDateTime(TimeZone.currentSystemDefault())
        val project = sesameProject
            .copy(
                duration = startDate..endDate
            )
        asserter
            .assertTrue(
                actual = project.isOver(),
                message = "This project is over ! the end date is in the past",
            )
    }


}