/**
 * ======================================================
 * LoginPage.jsx
 * Handles user authentication (Login flow).
 * - Uses react-hook-form for validation
 * - Stores JWT token and username on successful login
 * - Displays toast notifications for success/error
 * - Shows loading spinner during API call
 * ======================================================
 */

import { useForm } from "react-hook-form";
import { useNavigate } from "react-router-dom";
import { useState } from "react";
import toast from "react-hot-toast";
import { loginUser } from "../services/api";
import { useAuth } from "../hooks/useAuth";
import Loader from "../components/Loader";
import { MESSAGES, FORM_RULES, ROUTES, STORAGE_KEYS } from "../constants";
import { handleApiError } from "../utils/errorHandler";

/**
 * LoginPage Component
 * Provides a simple login form to authenticate users.
 *
 * @component
 * @returns {JSX.Element} The login form UI with validation, toasts, and spinner.
 */
export default function LoginPage() {
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm();

  const { login } = useAuth();
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  /**
   * Handles user login submission.
   * On success: saves token and username, updates context, and redirects.
   * On failure: displays user-friendly error toast.
   */
  const onSubmit = async (data) => {
    setLoading(true);
    try {
      const res = await loginUser(data);
      const token = res?.data?.token;
      const username = data.username;

      if (token) {
        // Save token & username
        login(token, username);
        localStorage.setItem(STORAGE_KEYS.TOKEN, token);
        localStorage.setItem("username", username);

        toast.success(MESSAGES.LOGIN_SUCCESS);
        setTimeout(() => navigate(ROUTES.USERS), 700);
      } else {
        toast.error(MESSAGES.TOKEN_NOT_FOUND);
      }
    } catch (error) {
      const msg = handleApiError(error);
      toast.error(msg);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="flex flex-col items-center justify-center min-h-screen bg-gray-100 px-3">
      {loading ? (
        <Loader text="Logging in..." />
      ) : (
        <form
          onSubmit={handleSubmit(onSubmit)}
          className="bg-white p-6 rounded-lg shadow-md w-full max-w-sm mx-auto"
        >
          <h2 className="text-2xl font-semibold text-center mb-4 text-gray-700">
            Login
          </h2>

          {/* Username Field */}
          <input
            type="text"
            {...register("username", FORM_RULES.USERNAME)}
            placeholder="Username"
            className="border p-2 w-full rounded mb-3 focus:ring-2 focus:ring-blue-500 outline-none"
          />
          {errors.username && (
            <p className="text-red-500 text-sm mb-2">
              {errors.username.message}
            </p>
          )}

          {/* Password Field */}
          <input
            type="password"
            {...register("password", FORM_RULES.PASSWORD)}
            placeholder="Password"
            className="border p-2 w-full rounded mb-3 focus:ring-2 focus:ring-blue-500 outline-none"
          />
          {errors.password && (
            <p className="text-red-500 text-sm mb-2">
              {errors.password.message}
            </p>
          )}

          {/* Submit Button */}
          <button
            type="submit"
            disabled={loading}
            className="w-full bg-blue-600 hover:bg-blue-700 disabled:bg-gray-400 disabled:cursor-not-allowed text-white py-2 rounded font-semibold transition-colors"
          >
            {loading ? "Please wait..." : "Login"}
          </button>
        </form>
      )}
    </div>
  );
}
