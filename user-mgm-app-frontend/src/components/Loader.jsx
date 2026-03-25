/**
 * ======================================================
 * Loader.jsx
 * A simple reusable loading spinner component.
 * Used during async operations like login, register, or data fetch.
 * ======================================================
 */

/**
 * Loader Component
 */
export default function Loader({ text = 'Loading...' }) {
  return (
    <div className="flex flex-col items-center justify-center py-8">
      {/* Spinner */}
      <div className="w-10 h-10 border-4 border-blue-600 border-t-transparent rounded-full animate-spin" />

      {/* Text below spinner */}
      <p className="mt-3 text-gray-600 text-sm font-medium">{text}</p>
    </div>
  );
}
