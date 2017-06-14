package Repositories

import java.util.concurrent.atomic.AtomicInteger
import javax.inject.Inject

import com.google.inject.ImplementedBy
import controllers.WorkStatusInput
import models.WorkStatus.WorkStatus
import models.{Subject, SubjectInputForm, WorkStatus}

import scala.collection.mutable

/**
  * Created by LenovoPC on 2017/06/13.
  */
@ImplementedBy(classOf[SubjectRepositoryImpl])
trait SubjectRepository {
  def getAll(): List[Subject]

  def getById(id: Int): Option[Subject]

  def create(subject: SubjectInputForm): Subject

  def update(id: Int, subject: SubjectInputForm): Option[Subject]

  def updateStatus(id: Int, status: WorkStatusInput): Option[Subject]

  def delete(id: Int): Boolean
}

class SubjectRepositoryImpl @Inject() extends SubjectRepository {

  private var subject: mutable.MutableList[Subject] = mutable.MutableList(
        Subject(1, "Subject1", "FreeText1", WorkStatus.Pending),
        Subject(2, "Subject1", "FreeText1", WorkStatus.Done)
      )

  private val atomicNum = new AtomicInteger(2)

  override def getAll(): List[Subject] = {
    subject.toList
  }

  override def getById(id: Int): Option[Subject] = {
    subject.find(s => s.id == id)
  }

  override def create(subject: SubjectInputForm): Subject = {
    val newSubject = Subject(atomicNum.incrementAndGet(), subject.name, subject.content, getStatus(subject.status))
    this.subject = this.subject :+ newSubject

    newSubject
  }

  override def update(id: Int, subject: SubjectInputForm): Option[Subject] = {
    val index = this.subject.indexWhere(s => s.id == id)

    if (index < 0)
      return null

    this.subject.update(index, Subject(id, subject.name, subject.content, getStatus(subject.status)))
    this.subject.get(index)
  }

  override def updateStatus(id: Int, workStatusInput: WorkStatusInput): Option[Subject] = {
    val index = this.subject.indexWhere(s => s.id == id)

    if (index < 0)
      return null

    val updateSubject = this.subject.get(index)

    this.subject.update(index, Subject(updateSubject.get.id, updateSubject.get.name, updateSubject.get.content, getStatus(workStatusInput.status)))
    this.subject.get(index)
  }

  override def delete(id: Int): Boolean = {
    val index = this.subject.indexWhere(s => s.id == id)

    if (index < 0)
      return false

    this.subject = this.subject.filterNot(s => s.id == id)
    true
  }

  private def getStatus(status: Byte): WorkStatus = {
    if (status == 1)
      WorkStatus.Pending
    else
      WorkStatus.Done
  }
}
