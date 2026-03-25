/**
 * ======================================================
 * HomePage.jsx
 * Public landing page for the User Management App.
 * Redirects authenticated users to the /users page.
 * Only shows Login for unauthenticated users.
 * ======================================================
 */

import { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { ROUTES } from "../constants";
import { useAuth } from "../hooks/useAuth";

export default function HomePage() {
  const navigate = useNavigate();
  const { isAuthenticated } = useAuth();

  useEffect(() => {
    if (isAuthenticated) navigate(ROUTES.USERS);
  }, [isAuthenticated, navigate]);

  const goTo = (path) => navigate(path);

  return (
    <div className="flex flex-col items-center justify-center h-screen bg-gray-50">
      <h1 className="text-4xl font-bold mb-6 text-blue-700">
        User Management App
      </h1>

      <p className="text-gray-600 mb-8">
        Please login to manage users.
      </p>

      <div className="flex space-x-4">
        <button
          onClick={() => goTo(ROUTES.LOGIN)}
          className="bg-blue-600 hover:bg-blue-700 text-white px-6 py-2 rounded-lg shadow transition-colors"
        >
          Login
        </button>
      </div>
    </div>
  );
}