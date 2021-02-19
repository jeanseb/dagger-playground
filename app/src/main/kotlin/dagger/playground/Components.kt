package dagger.playground

import dagger.Component
import dagger.Subcomponent
import javax.inject.Singleton

@Singleton
@Component
interface CompanyComponent {
    fun engineeringDepartmentComponent(): EngineeringDepartmentComponent
}

@EngineeringScope
@Subcomponent
interface EngineeringDepartmentComponent {
    fun androidTeam(): AndroidTeam
}
