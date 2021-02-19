package dagger.playground

fun main() {
    // Get an instance of the parent component.
    val companyComponent = DaggerCompanyComponent.create()

    // Print the Android team.
    println(companyComponent.engineeringDepartmentComponent().androidTeam()) // Prints an hash code

    // Print the Android team again.
    // We expect to get the same Android team, because AndroidTeam class is scoped with @EngineeringScope.
    // BUT IT'S NOT THE CASE! We get a different Android team.
    println(companyComponent.engineeringDepartmentComponent().androidTeam()) // Prints a different hash code

    // Why does it happen?
    // Because the scope works only within the SAME INSTANCE of the engineering department subcomponent. But every time
    // we call companyComponent.engineeringDepartmentComponent(), we get a new instance of the engineering department
    // subcomponent.
    val department1 = companyComponent.engineeringDepartmentComponent().also { println(it) }
    val department2 = companyComponent.engineeringDepartmentComponent().also { println(it) } // department2 != department1

    // Let's check the consequence on the scoped AndroidTeam:
    // If we get the Android team from the same instance of the engineering department subcomponent, the scope works:
    println(department1.androidTeam()) // Prints an hash code
    println(department1.androidTeam()) // Prints the same hash code

    // But if we try to get the Android team from different instances of the engineering department subcomponent, the
    // scope doesn't work anymore:
    println(department1.androidTeam()) // Prints an hash code
    println(department2.androidTeam()) // Prints a different hash code

    // CONCLUSION
    // A Dagger scope works only within the SAME INSTANCE of a component or subcomponent.
    // When we want the get the same instance of a class, we have to get it from the same instance of a (sub)component,
    // otherwise it won't work.
}
