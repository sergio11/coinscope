package com.dreamsoftware.coinscope.data.remote.exception

/**
 * Enum class representing different types of network-related errors.
 */
enum class NetworkError {
    REQUEST_TIMEOUT,    // The request timed out.
    TOO_MANY_REQUESTS,  // Too many requests were sent in a short period.
    NO_INTERNET,        // No internet connection available.
    SERVER_ERROR,       // An error occurred on the server side.
    SERIALIZATION,      // Failed to serialize or deserialize data.
    UNKNOWN             // An unknown error occurred.
}


/**
 * Exception representing an error that occurred during a network operation.
 *
 * This class encapsulates a specific `NetworkError` and optionally includes
 * additional details or a cause for the error.
 *
 * @property error The specific type of network error that occurred.
 * @property details Optional additional details about the error.
 */
class NetworkException(
    val error: NetworkError,
    message: String,
    cause: Throwable? = null,
    val details: String? = null
) : Exception(message, cause) {
    override fun toString(): String {
        return "NetworkException(error=$error, message=${message ?: "N/A"}, details=$details, cause=${cause?.javaClass?.simpleName})"
    }
}