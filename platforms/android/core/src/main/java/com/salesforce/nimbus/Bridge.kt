package com.salesforce.nimbus

/**
 * Defines an object which will be a bridge between a native [Plugin] and a [JavascriptEngine],
 * such as an Android WebView or V8. [EncodedType] represents the encoded type the
 * [JavascriptEngine] expects.
 */
interface Bridge<JavascriptEngine, EncodedType> {

    /**
     * Detaches the [Bridge] from a [JavascriptEngine].
     */
    fun detach()

    /**
     * Abstract builder class for the [Bridge] implementation to implement in order to build a [Bridge] instance.
     */
    abstract class Builder<JavascriptEngine, EncodedType, B : Bridge<JavascriptEngine, EncodedType>> {
        protected val builderBinders = mutableListOf<Binder<JavascriptEngine, EncodedType>>()

        /**
         * Adds a plugin [Binder] to the [Bridge].
         */
        fun bind(binder: Binder<JavascriptEngine, EncodedType>): Builder<JavascriptEngine, EncodedType, B> {
            builderBinders.add(binder)
            return this
        }

        /**
         * Adds a plugin [Binder] to the [Bridge].
         */
        fun bind(builder: Builder<JavascriptEngine, EncodedType, B>.() -> Binder<JavascriptEngine, EncodedType>): Builder<JavascriptEngine, EncodedType, B> {
            builderBinders.add(builder())
            return this
        }

        /**
         * Attaches the [Bridge] to a [JavascriptEngine].
         */
        abstract fun attach(javascriptEngine: JavascriptEngine): B
    }
}
