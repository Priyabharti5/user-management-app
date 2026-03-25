/**
 * ======================================================
 * api.js
 * Axios setup for backend communication.
 * Uses centralized constants and attaches JWT token
 * for all protected endpoints (add user + search users).
 * ======================================================
 */

import axios from 'axios';
import { API_BASE_URL, STORAGE_KEYS, ENDPOINTS } from '../constants';

/**
 * Create an Axios instance for consistent configuration
 */
const api = axios.create({
  baseURL: API_BASE_URL,
  headers: { 'Content-Type': 'application/json' },
});

/**
 * Request Interceptor
 * Adds JWT Bearer token to all protected endpoints automatically.
 * Both Add User and Search Users require authentication.
 */
api.interceptors.request.use((config) => {
  const token = localStorage.getItem(STORAGE_KEYS.TOKEN);

  // Both add-user and search-user are now protected endpoints
  const protectedEndpoints = [
    ENDPOINTS.USERS.SEARCH,
    ENDPOINTS.USERS.ADD,     // <-- now protected: requires login
  ];

  const isProtected = protectedEndpoints.some((endpoint) =>
    config.url?.includes(endpoint),
  );

  if (isProtected && token) {
    config.headers.Authorization = `Bearer ${token}`;
  }

  return config;
});

/**
 * Register a new user (Protected — requires JWT token)
 * Only logged-in users can add new users.
 */
export const registerUser = (userData) =>
  api.post(ENDPOINTS.USERS.ADD, userData);

/**
 * Login user and retrieve JWT token (No token required)
 */
export const loginUser = (credentials) =>
  api.post(ENDPOINTS.AUTH.LOGIN, credentials);

/**
 * Search users (Protected endpoint — requires JWT token)
 */
export const searchUsers = (filters) =>
  api.post(ENDPOINTS.USERS.SEARCH, filters);

export default api;