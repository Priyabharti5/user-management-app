import { Link, useNavigate, useLocation } from 'react-router-dom';
import { useAuth } from '../hooks/useAuth';
import { ROUTES } from '../constants';

export default function Navbar() {
  const navigate = useNavigate();
  const location = useLocation();
  const { isAuthenticated, username, logout } = useAuth();

  /**
   * If logged in → go to Users page.
   * If not logged in → go to Login page.
   */
  const goToHome = () => {
    if (isAuthenticated) {
      navigate(ROUTES.USERS);
    } else {
      navigate(ROUTES.LOGIN);
    }
  };

  return (
    <nav className="bg-gray-900 text-white px-6 py-3 flex justify-between items-center shadow-md">
      {/* App Title / Logo */}
      <h1
        onClick={goToHome}
        className="text-xl font-bold cursor-pointer hover:text-gray-300 transition-colors"
      >
        User Management App
      </h1>

      {/* Right-side Menu */}
      <div className="flex items-center space-x-4">
        {/* =============== When NOT Logged In =============== */}
        {!isAuthenticated ? (
          <>
            <Link
              to={ROUTES.LOGIN}
              className="hover:text-blue-400 font-medium transition-colors"
            >
              Login
            </Link>
          </>
        ) : (
          <>
            {/* =============== When Logged In =============== */}
            <span className="text-yellow-300 font-semibold opacity-0 animate-fadeIn">
              Hi, {username || 'User'}
            </span>

            {location.pathname !== ROUTES.USERS && (
              <Link
                to={ROUTES.USERS}
                className="hover:text-yellow-400 font-medium transition-colors"
              >
                Users
              </Link>
            )}

            {location.pathname !== ROUTES.REGISTER && (
              <Link
                to={ROUTES.REGISTER}
                className="hover:text-green-400 font-medium transition-colors"
              >
                Register
              </Link>
            )}

            <button
              onClick={logout}
              className="bg-red-600 hover:bg-red-700 text-white px-3 py-1 rounded transition-colors"
            >
              Logout
            </button>
          </>
        )}
      </div>
    </nav>
  );
}
