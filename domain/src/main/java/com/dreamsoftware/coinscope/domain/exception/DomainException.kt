package com.dreamsoftware.coinscope.domain.exception


/**
 * Base class for all repository-related exceptions in the domain layer.
 *
 * This class can be extended to create more specific exceptions for repository operations.
 */
open class DomainRepositoryException(message: String? = null, cause: Throwable? = null) : Exception(message, cause)

/**
 * Exception thrown when there is an error retrieving the list of coins from the data source.
 */
class CoinsRetrievalException(message: String? = "Failed to retrieve the list of coins.", cause: Throwable? = null)
    : DomainRepositoryException(message, cause)

/**
 * Exception thrown when there is an error retrieving the historical data for a specific coin.
 */
class CoinHistoryRetrievalException(message: String? = "Failed to retrieve the historical data for the coin.", cause: Throwable? = null)
    : DomainRepositoryException(message, cause)

/**
 * Exception thrown when there is a general error in performing a repository operation (e.g., unknown errors).
 */
class RepositoryOperationException(message: String? = null, cause: Throwable? = null)
    : DomainRepositoryException(message, cause)