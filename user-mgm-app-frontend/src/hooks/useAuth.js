/**
 * ======================================================
 * useAuth.js
 * Global authentication context and helper hook.
 * - Manages login, logout, and route protection.
 * - Persists JWT token and username in localStorage.
 * ======================================================
 */

import { createContext, useContext, useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { ROUTES, STORAGE_KEYS } from '../constants';

const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const navigate = useNavigate();
  const [isAuthenticated, setIsAuthenticated] = useState(false);
  const [username, setUsername] = useState(null);

  /**
   * Check for existing token and username on app mount.
   * If found, set authentication state and redirect away from login.
   */
  useEffect(() => {
    const token = localStorage.getItem(STORAGE_KEYS.TOKEN);
    const storedUser = localStorage.getItem('username');
    if (token) {
      setIsAuthenticated(true);
      setUsername(storedUser || null);
      // Already logged in — skip login page, go straight to users
      navigate(ROUTES.USERS, { replace: true });
    }
    // eslint-disable-next-line
  }, []);

  /**
   * Log in the user by saving token and username to localStorage.
   */
  const login = (token, username) => {
    localStorage.setItem(STORAGE_KEYS.TOKEN, token);
    if (username) localStorage.setItem('username', username);
    setUsername(username || null);
    setIsAuthenticated(true);
  };

  /**
   * Log out the user by clearing localStorage and navigating to login page.
   */
  const logout = () => {
    localStorage.removeItem(STORAGE_KEYS.TOKEN);
    localStorage.removeItem('username');
    setIsAuthenticated(false);
    setUsername(null);
    navigate(ROUTES.LOGIN, { replace: true });
  };

  /**
   * Protects private routes.
   * Redirects user to Login if no valid token exists.
   */
  const requireAuth = () => {
    const token = localStorage.getItem(STORAGE_KEYS.TOKEN);
    if (!token) navigate(ROUTES.LOGIN, { replace: true });
  };

  return (
    <AuthContext.Provider
      value={{ isAuthenticated, username, login, logout, requireAuth }}
    >
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => useContext(AuthContext);
