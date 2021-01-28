package dagger.playground

import dagger.Component
import javax.inject.Inject

fun main() {
    // For each interface we annotate with the @Component annotation, Dagger generates an implementation of this interface.
    // This implementation has the same name than the interface, but it's prefixed with "Dagger".
    // Here, Dagger has generated a DaggerParentComponent implementation for the ParentComponent interface.
    val component: ParentComponent = DaggerParentComponent.create()

    // Once the component is created, it acts like a black box: we can ask it to provide an instance of a class.
    // Here, we don't know exactly how Dagger builds a Car, but the important thing is that we asked a Car and Dagger gave us a Car.
    val car = component.getCar()
    println(car)

    // We can also ask Dagger to fill the attributes of a class that we have already created ourselves.
    // Here, we have a parking. By calling component.inject(parking), we ask Dagger to fill every attribute of this
    // parking that we have annotated with the @Inject annotation.
    val parking = Parking()
    component.inject(parking)
    println(parking.car)

}

class Parking {

    @Inject // @Inject on a class attribute means: "I want Dagger to fill this attribute when we call component.inject(this class)."
    lateinit var car: Car

    override fun toString(): String {
        return "I'm a parking with this car:\n  > $car"
    }

}


// @Inject on a class constructor means: "If Dagger needs to build a wheel, it can use this constructor"
class Wheel @Inject constructor() {
    override fun toString(): String {
        return "Michelin"
    }
}


// If Dagger needs to build a Car, it will use this constructor. But because this constructor needs a Wheel, it will
// build a Wheel first, and then build the Car.
data class Car @Inject constructor(val wheel: Wheel) {
    override fun toString(): String {
        return "I'm a Renault with this wheel: $wheel"
    }
}



@Component
interface ParentComponent {
    // We tell Dagger that we want it to generate an implementation that will be able to build cars.
    // We rarely use this technique in Android.
    fun getCar(): Car

    // We tell Dagger that we want it to generate an implementation that will fill attributes of a Parking.
    // We often use this technique to inject things in Android classes, for example: fun inject(activity: FooActivity)
    fun inject(parking: Parking)
}
