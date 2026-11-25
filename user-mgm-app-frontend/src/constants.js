/**
 * ======================================================
 * constants.js
 * Centralized configuration for API endpoints, routes,
 * messages, and form validation rules.
 * ======================================================
 */

// Base URL of your Spring Boot Backend
export const API_BASE_URL = "http://localhost:8080/api";

/**
 * Storage keys used in localStorage
 */
export const STORAGE_KEYS = {
  TOKEN: "token",
};

/**
 * Application routes for navigation
 * All route paths in one place.
 * If a route ever changes, update it here only.
 */
export const ROUTES = {
  LOGIN: "/login",
  REGISTER: "/register",
  USERS: "/users",
  HOME: "/",
};

/**
 * API endpoints for backend integration
 */
export const ENDPOINTS = {
  USERS: {
    ADD: "/users/add",        // POST - Register/Add new user
    SEARCH: "/users/search",  // POST - Search users (protected)
    DELETE: "/users/delete"
  },
  AUTH: {
    LOGIN: "/auth/login",     // POST - Login and get JWT token
  },
};

/**
 * User-facing messages for UI feedback (toast notifications)
 */
export const MESSAGES = {
  LOGIN_SUCCESS: "Login successful!",
  LOGIN_FAILED: "Invalid credentials.",
  TOKEN_NOT_FOUND: "Token not found in response.",
  FETCH_FAILED: "Failed to fetch users. Please try again.",
};

/**
 * HTTP status code constants for clean comparisons in error handler
 */
export const HTTP_STATUS = {
  OK: 200,
  CREATED: 201,
  BAD_REQUEST: 400,
  UNAUTHORIZED: 401,
  FORBIDDEN: 403,
  NOT_FOUND: 404,
  CONFLICT: 409,
  INTERNAL_SERVER_ERROR: 500,
};

/**
 * React Hook Form validation rules for each field
 */
export const FORM_RULES = {
  USERNAME: {
    required: "Username is required",
    minLength: { value: 3, message: "Username must be at least 3 characters" },
    maxLength: { value: 50, message: "Username cannot exceed 50 characters" },
  },
  FIRST_NAME: {
    required: "First name is required",
    pattern: { value: /^[A-Za-z]+$/, message: "Only letters are allowed" },
    maxLength: { value: 50, message: "Cannot exceed 50 characters" },
  },
  LAST_NAME: {
    required: "Last name is required",
    pattern: { value: /^[A-Za-z]+$/, message: "Only letters are allowed" },
    maxLength: { value: 50, message: "Cannot exceed 50 characters" },
  },
  EMAIL: {
    required: "Email is required",
    pattern: {
      value: /^[^\s@]+@[^\s@]+\.[^\s@]+$/,
      message: "Invalid email format",
    },
    maxLength: { value: 100, message: "Email cannot exceed 100 characters" },
  },
  PASSWORD: {
    required: "Password is required",
    minLength: { value: 8, message: "Password must be at least 8 characters" },
    maxLength: { value: 64, message: "Password cannot exceed 64 characters" },
  },
};
