module.exports = {
  root: true,
  parser: '@typescript-eslint/parser',
  parserOptions: {
    project: "tsconfig.eslint.json"
  },
  plugins: [
    '@typescript-eslint',
  ],
  extends: [
    'airbnb-typescript/base',
    'prettier'
  ],
  rules: {
    'import/prefer-default-export': 0,
    'no-console': 0,
  }
};
