package ar.edu.utn.tadp.argentina_express.objects_solution
import java.util.Date
import java.util.Calendar

class Shipping(val origin:Office, val destiny:Office, val pack:Package, val transport:Transport, var date: Date = new Date) {
  def cost : Double = {
    var cost = transport.travelCost(origin, destiny) + pack.cost
    if (origin == Central && isLastWeek && !transport.isPlane) { cost = 1.2 * cost }

    cost
  }
  
  def isLastWeek : Boolean = {
    var calendar = Calendar.getInstance()
    var calendar2 = Calendar.getInstance()
    calendar.setTime(date)
    calendar2.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));

    
     var milsecs1= calendar.getTimeInMillis()
     var milsecs2 = calendar2.getTimeInMillis()
     var diff = milsecs2 - milsecs1
     var ddays = diff / (24 * 60 * 60 * 1000)
     
     ddays < 7;
    
  }
}