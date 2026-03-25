/**
 * ===============================================================
 * RegisterPage.jsx
 * Protected page — only accessible by authenticated (logged-in) users.
 * Handles user registration with validation, loader, and toast feedback.
 * Uses centralized constants and global error handler.
 * ===============================================================
 */

import { useForm } from "react-hook-form";
import { useState, useEffect } from "react";
import toast from "react-hot-toast";
import { registerUser } from "../services/api";
import Loader from "../components/Loader";
import { handleApiError } from "../utils/errorHandler";
import { FORM_RULES, MESSAGES } from "../constants";
import { useAuth } from "../hooks/useAuth";

/**
 * Component for user registration page.
 * - Protected: redirects to login if user is not authenticated.
 * - Displays loader during API call and toast notifications for feedback.
 *
 * @returns {JSX.Element} Registration form with validation and feedback.
 */
export default function RegisterPage() {
  const { requireAuth } = useAuth();

  const {
    register,
    handleSubmit,
    formState: { errors },
    reset,
  } = useForm();

  const [loading, setLoading] = useState(false);

  /**
   * On mount: protect the route.
   * Redirects unauthenticated users to /login.
   */
  useEffect(() => {
    requireAuth();
    // eslint-disable-next-line
  }, []);

  /**
   * Handles user registration form submission.
   * Calls backend API and displays success or error message using toasts.
   */
  const onSubmit = async (data) => {
    setLoading(true);
    try {
      const res = await registerUser(data);
      toast.success(`User created successfully! ID: ${res.data.userId}`);
      reset();
    } catch (error) {
      const message = handleApiError(error);
      toast.error(message || MESSAGES.FETCH_FAILED);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="flex flex-col items-center justify-center min-h-screen bg-gray-100 px-3">
      {loading ? (
        <Loader text="Creating user..." />
      ) : (
        <form
          onSubmit={handleSubmit(onSubmit)}
          className="bg-white p-6 rounded-lg shadow-md w-full max-w-md mx-auto"
        >
          <h2 className="text-2xl font-semibold text-center mb-4 text-gray-700">
            Register User
          </h2>

          {/* UserName */}
          <div className="mb-3">
            <label className="block text-sm font-medium text-gray-700 mb-1">
              UserName
            </label>
            <input
              type="text"
              {...register("username", FORM_RULES.USERNAME)}
              className="border rounded w-full p-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
            {errors.username && (
              <p className="text-red-500 text-sm mt-1">
                {errors.username.message}
              </p>
            )}
          </div>

          {/* First Name */}
          <div className="mb-3">
            <label className="block text-sm font-medium text-gray-700 mb-1">
              First Name
            </label>
            <input
              type="text"
              {...register("firstName", FORM_RULES.FIRST_NAME)}
              className="border rounded w-full p-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
            {errors.firstName && (
              <p className="text-red-500 text-sm mt-1">
                {errors.firstName.message}
              </p>
            )}
          </div>

          {/* Last Name */}
          <div className="mb-3">
            <label className="block text-sm font-medium text-gray-700 mb-1">
              Last Name
            </label>
            <input
              type="text"
              {...register("lastName", FORM_RULES.LAST_NAME)}
              className="border rounded w-full p-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
            {errors.lastName && (
              <p className="text-red-500 text-sm mt-1">
                {errors.lastName.message}
              </p>
            )}
          </div>

          {/* Email */}
          <div className="mb-3">
            <label className="block text-sm font-medium text-gray-700 mb-1">
              Email
            </label>
            <input
              type="email"
              {...register("email", FORM_RULES.EMAIL)}
              className="border rounded w-full p-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
            {errors.email && (
              <p className="text-red-500 text-sm mt-1">
                {errors.email.message}
              </p>
            )}
          </div>

          {/* Password */}
          <div className="mb-4">
            <label className="block text-sm font-medium text-gray-700 mb-1">
              Password
            </label>
            <input
              type="password"
              {...register("password", FORM_RULES.PASSWORD)}
              className="border rounded w-full p-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
            {errors.password && (
              <p className="text-red-500 text-sm mt-1">
                {errors.password.message}
              </p>
            )}
          </div>

          {/* Submit Button */}
          <button
            type="submit"
            disabled={loading}
            className="w-full bg-green-600 hover:bg-green-700 disabled:bg-gray-400 disabled:cursor-not-allowed text-white py-2 rounded font-semibold transition-all"
          >
            {loading ? "Please wait..." : "Register"}
          </button>
        </form>
      )}
    </div>
  );
}