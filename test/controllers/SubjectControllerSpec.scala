package controllers

import Repositories.SubjectRepositoryImpl
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.libs.json.Json
import play.api.test.FakeRequest
import play.api.test.Helpers._

/**
  * Created by VerDev06 on 2017/06/14.
  */
class SubjectControllerSpec extends PlaySpec with GuiceOneAppPerSuite {
  "Test SubjectController" should {
    val subjectRepository = new SubjectRepositoryImpl

    "get all subject should return http status OK" in {
      val controller = new SubjectController(subjectRepository)
      val result = controller.getSubjects().apply(FakeRequest())

      status(result) mustBe OK
    }

    "get subject by valid id should return http status OK" in {
      val controller = new SubjectController(subjectRepository)
      val result = controller.getSubjectById(1).apply(FakeRequest())

      status(result) mustBe OK
    }

    "get subject by invalid id should return status OK" in {
      val controller = new SubjectController(subjectRepository)
      val result = controller.getSubjectById(100).apply(FakeRequest())

      status(result) mustBe NOT_FOUND
    }

    "create subject should return http status CREATED" in {
      val request = FakeRequest(POST, "/subject").withJsonBody(Json.parse("""{ "name": "SubjectTest", "content": "freeTextTest", "status": 1 }"""))
      val controller = new SubjectController(subjectRepository)
      val result = controller.createSubject().apply(request)
      status(result) mustEqual CREATED
    }

    "create subject with invalid data should return http status BAD_REQUEST" in {
      val request = FakeRequest(POST, "/subject").withJsonBody(Json.parse("""{ "name": "SubjectTest", "content": 123123, "status": "invalid" }"""))
      val controller = new SubjectController(subjectRepository)
      val result = controller.createSubject().apply(request)
      status(result) mustEqual BAD_REQUEST
      contentAsString(result) mustEqual "Invalid data"
    }

    "update subject should return http status OK" in {
      val request = FakeRequest(PUT, "/subject/1").withJsonBody(Json.parse("""{ "name": "SubjectTest", "content": "freeTextTest", "status": 1 }"""))
      val controller = new SubjectController(subjectRepository)
      val result = controller.updateSubject(1).apply(request)
      status(result) mustEqual OK
    }

    "update subject with invalid id should return http status NOT_FOUND" in {
      val request = FakeRequest(PUT, "/subject/100").withJsonBody(Json.parse("""{ "name": "SubjectTest", "content": "freeTextTest", "status": 1 }"""))
      val controller = new SubjectController(subjectRepository)
      val result = controller.updateSubject(100).apply(request)
      status(result) mustEqual NOT_FOUND
    }

    "update status should return http status OK" in {
      val request = FakeRequest(PATCH, "/subject/1").withJsonBody(Json.parse("""{"status": 1 }"""))
      val controller = new SubjectController(subjectRepository)
      val result = controller.updateStatus(1).apply(request)
      status(result) mustEqual OK
    }

    "update status with invalid id should return http status OK" in {
      val request = FakeRequest(PATCH, "/subject/100").withJsonBody(Json.parse("""{"status": 1 }"""))
      val controller = new SubjectController(subjectRepository)
      val result = controller.updateStatus(100).apply(request)
      status(result) mustEqual NOT_FOUND
    }

    "update status with invalid data should return http status BAD_REQUEST" in {
      val request = FakeRequest(PATCH, "/subject/1").withJsonBody(Json.parse("""{"status": "invalid" }"""))
      val controller = new SubjectController(subjectRepository)
      val result = controller.updateStatus(1).apply(request)
      status(result) mustEqual BAD_REQUEST
      contentAsString(result) mustEqual "Invalid status"
    }

    "delete subject should return http status OK" in {
      val request = FakeRequest(DELETE, "/subject/1")
      val controller = new SubjectController(subjectRepository)
      val result = controller.deleteSubject(1).apply(request)
      status(result) mustEqual OK
      contentAsString(result) mustEqual "Subject was delete"
    }
  }
}
