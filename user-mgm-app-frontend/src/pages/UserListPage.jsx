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

export default function UserListPage() {
  const { requireAuth, isAuthenticated } = useAuth();
  const { register, handleSubmit, reset, getValues } = useForm({
    defaultValues: {
      userId: '',
      username: '',
      firstName: '',
      lastName: '',
      email: '',
    },
  });

  const [users, setUsers] = useState([]);
  const [totalUsers, setTotalUsers] = useState(0);
  const [loading, setLoading] = useState(false);
  const [hasLoadedOnce, setHasLoadedOnce] = useState(false);

  // 2.
  const [sortByField, setSortByField] = useState(null);
  const [sortAscending, setSortAscending] = useState(true);

  const hasFetchedRef = useRef(false);
  const navigate = useNavigate();

  useEffect(() => {
    requireAuth();
    // eslint-disable-next-line
  }, []);

  useEffect(() => {
    if (isAuthenticated && !hasFetchedRef.current) {
      hasFetchedRef.current = true;
      handleSearch({}, false, null, true);
    }
    // eslint-disable-next-line
  }, [isAuthenticated]);

  const buildPayload = (formData) => {
    const payload = {};
    if (formData.userId && String(formData.userId).trim() !== '')
      payload.userId = parseInt(formData.userId, 10);
    ['username', 'firstName', 'lastName', 'email'].forEach((key) => {
      if (formData[key]?.trim()) payload[key] = formData[key].trim();
    });
    return payload;
  };

  // 4. 
  const handleSearch = async (
    data,
    showToast = true,
    currentSortField = sortByField,
    currentSortAscending = sortAscending,
  ) => {
    if (loading) return;
    setLoading(true);
    try {
      const payload = buildPayload(data);

      if (currentSortField != null) {
        payload.sortByField = currentSortField;
        payload.sortAscending = currentSortAscending;
      }

      const res = await searchUsers(payload);
      const fetchedUsers = res.data || [];

      setUsers(fetchedUsers);

      if (showToast) toast.dismiss();

      if (
        Object.keys(payload).length === 0 ||
        (currentSortField && Object.keys(buildPayload(data)).length === 0)
      ) {
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

  // 3.
  const handleSort = (field, ascending) => {
    setSortByField(field);
    setSortAscending(ascending);

    const currentFormData = getValues();
    handleSearch(currentFormData, false, field, ascending);
  };

  // 5.
  const onSubmit = (data) =>
    handleSearch(data, true, sortByField, sortAscending);

  const handleReset = () => {
    reset({
      userId: '',
      username: '',
      firstName: '',
      lastName: '',
      email: '',
    });
    // 6.
    setSortByField(null);
    setSortAscending(true);
    handleSearch({}, true, null, true);
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
          {...register('username')}
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
                <th className="p-2">
                    First Name
                    {/** 1. */}
                  <button onClick={() => handleSort('firstName', true)}>
                    ▲
                  </button>
                  <button onClick={() => handleSort('firstName', false)}>
                    ▼
                  </button>
                </th>
                <th className="p-2">
                  Last Name
                  <button onClick={() => handleSort('lastName', true)}>
                    ▲
                  </button>
                  <button onClick={() => handleSort('lastName', false)}>
                    ▼
                  </button>
                </th>
                <th className="p-2">
                  Email
                  <button onClick={() => handleSort('email', true)}>▲</button>
                  <button onClick={() => handleSort('email', false)}>▼</button>
                </th>
                <th className="p-2">
                  User Name
                  <button onClick={() => handleSort('username', true)}>
                    ▲
                  </button>
                  <button onClick={() => handleSort('username', false)}>
                    ▼
                  </button>
                </th>
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
                    <td className="p-2">{u.username}</td>
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
