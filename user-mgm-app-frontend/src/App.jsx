/**
 * ======================================================
 * App.jsx
 * Root component that defines the main router configuration.
 * Uses Router at top level so hooks like useNavigate work inside AuthProvider.
 * Adds Toaster for global toast notifications.
 * ======================================================
 */

import { BrowserRouter as Router, Routes, Route, Navigate } from "react-router-dom";
import { Toaster } from "react-hot-toast";
import { AuthProvider } from "./hooks/useAuth";
import Navbar from "./components/Navbar";
import LoginPage from "./pages/LoginPage";
import RegisterPage from "./pages/RegisterPage";
import UserListPage from "./pages/UserListPage";
import { ROUTES } from "./constants";

/**
 * Application entry point component.
 * Defines routing, authentication provider, and global toast configuration.
 * The root path "/" redirects directly to "/login".
 */
export default function App() {
  return (
    <Router>
      <AuthProvider>
        <Navbar />

        <Routes>
          <Route path={ROUTES.HOME} element={<Navigate to={ROUTES.LOGIN} replace />} />
          <Route path={ROUTES.LOGIN} element={<LoginPage />} />
          <Route path={ROUTES.REGISTER} element={<RegisterPage />} />
          <Route path={ROUTES.USERS} element={<UserListPage />} />
        </Routes>

        {/* Global Toast Notification Provider */}
        <Toaster
          position="top-center"
          toastOptions={{
            className: "bg-gray-900 text-white rounded shadow-md",
            duration: 3000,
          }}
        />
      </AuthProvider>
    </Router>
  );
}