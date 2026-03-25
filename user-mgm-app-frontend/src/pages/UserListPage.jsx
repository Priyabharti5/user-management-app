/**
 * ====================================================================
 * UserListPage.jsx
 * Displays list of users and allows searching by any or all fields.
 * Protected route - requires valid JWT token.
 * Shows loader and toast notifications for consistent feedback.
 * ====================================================================
 */

import React, { useEffect, useState, useRef } from 'react';
import { useForm } from 'react-hook-form';
import toast from 'react-hot-toast';
import { searchUsers } from '../services/api';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../hooks/useAuth';
import { handleApiError } from '../utils/errorHandler';
import Loader from '../components/Loader';
import { MESSAGES, ROUTES, STORAGE_KEYS } from '../constants';

/**
 * Renders a searchable and protected user list page.
 * - Allows filtering users by ID, username, first name, last name, or email.
 * - Displays total users count and search result count dynamically.
 */
export default function UserListPage() {
  const { requireAuth, isAuthenticated } = useAuth();
  const { register, handleSubmit, reset } = useForm({
    defaultValues: {
      userId: '',
      userName: '',
      firstName: '',
      lastName: '',
      email: '',
    },
  });

  const [users, setUsers] = useState([]);
  const [totalUsers, setTotalUsers] = useState(0);
  const [loading, setLoading] = useState(false);
  const [hasLoadedOnce, setHasLoadedOnce] = useState(false);

  /**
   * Ref flag to ensure the initial data fetch fires only once,
   * even if the component re-mounts or isAuthenticated changes.
   */
  const hasFetchedRef = useRef(false);

  const navigate = useNavigate();

  /**
   * On mount: protect the route.
   * Redirects unauthenticated users to /login.
   */
  useEffect(() => {
    requireAuth();
    // eslint-disable-next-line
  }, []);

  /**
   * Load all users once after confirmed authenticated.
   * useRef guard prevents duplicate calls on re-renders or re-mounts.
   */
  useEffect(() => {
    if (isAuthenticated && !hasFetchedRef.current) {
      hasFetchedRef.current = true;
      handleSearch({}, false);
    }
    // eslint-disable-next-line
  }, [isAuthenticated]);

  /**
   * Builds payload with only non-empty search fields.
   */
  const buildPayload = (formData) => {
    const payload = {};
    if (formData.userId && String(formData.userId).trim() !== '')
      payload.userId = parseInt(formData.userId, 10);
    ['userName', 'firstName', 'lastName', 'email'].forEach((key) => {
      if (formData[key]?.trim()) payload[key] = formData[key].trim();
    });
    return payload;
  };

  /**
   * Fetches users based on search criteria.
   * Handles both initial load and filtered search.
   */
  const handleSearch = async (data, showToast = true) => {
    if (loading) return;
    setLoading(true);

    try {
      const payload = buildPayload(data);
      const res = await searchUsers(payload);
      const fetchedUsers = res.data || [];
      setUsers(fetchedUsers);

      if (showToast) toast.dismiss();

      if (Object.keys(payload).length === 0) {
        setTotalUsers(fetchedUsers.length);
        if (!hasLoadedOnce && showToast) {
          toast.success(`Loaded ${fetchedUsers.length} total users`);
          setHasLoadedOnce(true);
        }
      } else {
        if (showToast) {
          if (fetchedUsers.length === 0) {
            toast('No users found.');
          } else if (fetchedUsers.length === 1) {
            toast.success('1 user found.');
          } else {
            toast.success(`${fetchedUsers.length} users found.`);
          }
        }
      }
    } catch (error) {
      const msg = handleApiError(error, () => {
        localStorage.removeItem(STORAGE_KEYS.TOKEN);
        navigate(ROUTES.LOGIN, { replace: true });
      });
      if (showToast) {
        toast.dismiss();
        toast.error(msg || MESSAGES.FETCH_FAILED);
      }
    } finally {
      setLoading(false);
    }
  };

  /**
   * Handles form submission to search users.
   */
  const onSubmit = (data) => handleSearch(data, true);

  /**
   * Resets all filters and reloads all users.
   */
  const handleReset = () => {
    reset({
      userId: '',
      userName: '',
      firstName: '',
      lastName: '',
      email: '',
    });
    handleSearch({}, true);
  };

  return (
    <div className="p-8 bg-gray-50 min-h-screen">
      <h1 className="text-3xl font-bold mb-6 text-gray-800">User List</h1>

      {/* ===================== Search Form ===================== */}
      <form
        onSubmit={handleSubmit(onSubmit)}
        className="bg-white shadow-md rounded-lg p-4 mb-6 grid grid-cols-1 md:grid-cols-5 gap-3"
      >
        <input
          type="number"
          {...register('userId')}
          placeholder="User ID"
          className="border rounded w-full p-2 focus:ring-2 focus:ring-blue-500 outline-none"
        />
        <input
          type="text"
          {...register('userName')}
          placeholder="User Name"
          className="border rounded w-full p-2 focus:ring-2 focus:ring-blue-500 outline-none"
        />
        <input
          type="text"
          {...register('firstName')}
          placeholder="First Name"
          className="border rounded w-full p-2 focus:ring-2 focus:ring-blue-500 outline-none"
        />
        <input
          type="text"
          {...register('lastName')}
          placeholder="Last Name"
          className="border rounded w-full p-2 focus:ring-2 focus:ring-blue-500 outline-none"
        />
        <input
          type="email"
          {...register('email')}
          placeholder="Email"
          className="border rounded w-full p-2 focus:ring-2 focus:ring-blue-500 outline-none"
        />

        <div className="md:col-span-5 flex gap-3 justify-end pt-2">
          <button
            type="button"
            onClick={handleReset}
            disabled={loading}
            className="px-4 py-2 rounded border disabled:opacity-60"
          >
            Reset
          </button>
          <button
            type="submit"
            disabled={loading}
            className="px-4 py-2 rounded bg-blue-600 hover:bg-blue-700 disabled:bg-gray-400 disabled:cursor-not-allowed text-white transition-colors"
          >
            {loading ? 'Searching...' : 'Search'}
          </button>
        </div>
      </form>

      {/* ===================== Summary Section ===================== */}
      {!loading && (
        <div
          key={users.length}
          className="mb-3 text-sm text-gray-700 transition-opacity duration-500 ease-in-out opacity-100"
        >
          {users.length > 0 ? (
            <span>
              {users.length === totalUsers && totalUsers > 0
                ? `Showing ${users.length} of ${totalUsers} users`
                : `${users.length} user${users.length > 1 ? 's' : ''} found`}
            </span>
          ) : (
            <span>No users found.</span>
          )}
        </div>
      )}

      {/* ===================== Table / Loader ===================== */}
      {loading ? (
        <Loader text="Fetching users..." />
      ) : (
        <div className="bg-white shadow-md rounded-lg overflow-x-auto">
          <table className="min-w-full">
            <thead>
              <tr className="bg-gray-200 text-left">
                <th className="p-2">First Name</th>
                <th className="p-2">Last Name</th>
                <th className="p-2">Email</th>
                <th className="p-2">User Name</th>
              </tr>
            </thead>
            <tbody>
              {users.length > 0 ? (
                users.map((u, index) => (
                  <tr
                    key={u.userId ?? `user-${index}`}
                    className="border-t hover:bg-gray-50"
                  >
                    <td className="p-2">{u.firstName}</td>
                    <td className="p-2">{u.lastName}</td>
                    <td className="p-2">{u.email}</td>
                    <td className="p-2">{u.userName}</td>
                  </tr>
                ))
              ) : (
                <tr>
                  <td
                    colSpan="4"
                    className="p-4 text-center text-gray-500 italic"
                  >
                    No users found. Try a different search.
                  </td>
                </tr>
              )}
            </tbody>
          </table>
        </div>
      )}
    </div>
  );
}