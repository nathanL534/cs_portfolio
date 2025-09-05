## Architecture of a Transformer


Step 1: Tokenizations
- Tokenizes the input and corresponds thoses integer ids with the tokens
- returns an array of size (B, L) B, being the batch size(i.e. num of sentences), and L being number of tokens. Each i,j index is the integer id for that token.

Step 2: Embedding Lookup
- Corresponds integer ID with dense vector
- The embedding matrix has shape:

  ![embedding equation](https://latex.codecogs.com/png.image?\dpi{150}&space;W^E%20\in%20\mathbb{R}^{V%20\times%20d_{\text{model}}})


