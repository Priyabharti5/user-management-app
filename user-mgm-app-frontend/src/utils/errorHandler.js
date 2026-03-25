/**
 * ======================================================
 * errorHandler.js
 * Centralized API error handling with user-friendly messages.
 * - Maps backend HTTP errors to clean messages.
 * - Handles unauthorized access gracefully.
 * ======================================================
 */

import { HTTP_STATUS } from "../constants";

/**
 * Predefined fallback error messages
 */
export const ERROR_MESSAGES = {
  NETWORK_ERROR: "Network error. Please check your connection.",
  TIMEOUT_ERROR: "Request timeout. Please try again.",
  UNAUTHORIZED: "Invalid username or password. Please login again.",
  FORBIDDEN: "You do not have permission to perform this action.",
  NOT_FOUND: "The requested resource was not found.",
  CONFLICT: "This resource already exists. Please try again.",
  VALIDATION_ERROR: "Please check your input and try again.",
  SERVER_ERROR: "Server error. Please try again later.",
  UNKNOWN_ERROR: "An unexpected error occurred. Please try again.",
};

/**
 * Converts an Axios error into a readable message string.
 * Handles both network failures and HTTP error responses.
 */
export const getErrorMessage = (error) => {
  // No response means network or timeout issue
  if (!error.response) {
    if (error.code === "ECONNABORTED") {
      return ERROR_MESSAGES.TIMEOUT_ERROR;
    }
    return ERROR_MESSAGES.NETWORK_ERROR;
  }

  const status = error.response.status;
  const data = error.response.data;

  // Map each status code to a readable message
  switch (status) {
    case HTTP_STATUS.BAD_REQUEST:
      return data?.message || ERROR_MESSAGES.VALIDATION_ERROR;
    case HTTP_STATUS.UNAUTHORIZED:
      return ERROR_MESSAGES.UNAUTHORIZED;
    case HTTP_STATUS.FORBIDDEN:
      return ERROR_MESSAGES.FORBIDDEN;
    case HTTP_STATUS.NOT_FOUND:
      return ERROR_MESSAGES.NOT_FOUND;
    case HTTP_STATUS.CONFLICT:
      return data?.message || ERROR_MESSAGES.CONFLICT;
    case HTTP_STATUS.INTERNAL_SERVER_ERROR:
      return ERROR_MESSAGES.SERVER_ERROR;
    default:
      return ERROR_MESSAGES.UNKNOWN_ERROR;
  }
};

/**
 * Handle API errors globally with optional auth callback
 * Main error handler used across all pages.
 * Gets the readable message AND optionally handles 401 redirects.
 */
export const handleApiError = (error, onUnauthorized = null) => {
  const message = getErrorMessage(error);

  // Optional redirect to login on unauthorized
  // If 401 and a redirect callback was provided, call it
  // Used in UserListPage to redirect to login when token expires
  if (error.response?.status === HTTP_STATUS.UNAUTHORIZED && onUnauthorized) {
    onUnauthorized();
  }

  return message;
};
