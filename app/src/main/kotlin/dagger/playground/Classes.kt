package dagger.playground

import javax.inject.Inject

@EngineeringScope
class AndroidTeam @Inject constructor() {
    override fun toString() = "I'm a scoped Android team with hash code ${hashCode()}"
}
