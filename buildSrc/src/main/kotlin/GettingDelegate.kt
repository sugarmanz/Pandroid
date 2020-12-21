import kotlin.reflect.KClass
import kotlin.reflect.KProperty

abstract class GettingDelegate {
    operator fun getValue(thisRef: Any?, property: KProperty<*>) = NamedDelegate(this::class, property.name)
        .getValue(thisRef, property)

    operator fun invoke(name: String) = NamedDelegate(this::class, name)

    class NamedDelegate(private val `class`: KClass<*>, private val name: String) {
        operator fun getValue(thisRef: Any?, property: KProperty<*>) = `class`.members
            .filterIsInstance<KProperty<String>>()
            .first { it.name == name }
            .call()
    }
}