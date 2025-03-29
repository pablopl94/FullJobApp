/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./src/**/*.{html,ts}",
  ],
  theme: {
    extend: {
      colors: {
        mustard: "#FAC402"
      }
    },
  },
  plugins: [require("daisyui")],
  daisyui: {
    themes: [], //Aquí se  puede poner lo que prefieras.
  },
};
