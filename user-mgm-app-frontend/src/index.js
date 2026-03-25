/**
 * ======================================================
 * index.js
 * Entry point for the React application.
 * - Mounts the <App /> component into the root div.
 * - Enables React.StrictMode for highlighting potential issues.
 * ======================================================
 */

import React from "react";
import ReactDOM from "react-dom/client";
import "./index.css";
import App from "./App";

/**
 * Create a React root and render the App component.
 * React.StrictMode is used only in development mode
 * to help identify potential problems.
 */
const root = ReactDOM.createRoot(document.getElementById("root"));
root.render(
  <React.StrictMode>
    <App />
  </React.StrictMode>
);
