/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./src/**/*.{html,ts}",
  ],
  theme: {
    extend: {
      colors: {
        mustard: "#FAC402", // Tu color mostaza
      },
      fontFamily: {
        sans: ['Poppins', 'sans-serif'], // Añadir Poppins como la fuente predeterminada
      },
    },
  },
  plugins: [require("daisyui")],
  daisyui: {
    themes: [], // Aquí se puede poner lo que prefieras
  },
};
