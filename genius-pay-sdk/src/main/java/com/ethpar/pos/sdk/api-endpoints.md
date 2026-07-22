
## § **6. API Endpoints**

The GeniusPay POS terminal communicates with the GeniusPay backend through the following API endpoints. All requests are authenticated using a signed JWT obtained during terminal enrollment.

#### **a) POST /api/v1/enrollment**

Purpose: Terminal enrollment to obtain signing keys for JWT authentication. Requires prior portal con�guration of the merchant and terminal.

|REQUEST||
|---|---|
|`terminalSerialNumber`|String — Hardware serial number of the NEXgo N82|
|`merchantId`|String — Merchant ID from portal con�guration|
|`activationCode`|String — One-time activation code generated in the portal|



|RESPONSE||
|---|---|
|`signingKeyId`|String — Key identi�er for future JWT signing|
|`signingKeySecret`|String — Secret key material (must be stored securely on terminal)|
|`tokenEndpoint`|String — URL to obtain JWT access tokens|
|`merchantName`|String — Merchant display name for receipts|



Signingkeys mustbe savedsecurely on the terminal. They are usedto sign allsubsequentAPIrequests.

#### **b) POST /api/v1/addFees**

Purpose: Calculate convenience and processing fees for a sale.

|REQUEST||
|---|---|
|`totalSale`|Decimal — Total sale amount (e.g., 100.00)|
|RESPONSE||
|`totalSale`|Decimal — Original sale amount|
|`convenienceFee`|Decimal — Fixed fee: $3.99|
|`processingFee`|Decimal — 1.5% of totalSale|
|`totalWithFees`|Decimal — totalSale + convenienceFee + processingFee|



EXAMPLE: For a $100.00 sale: Convenience fee = $3.99 | Processing fee = $1.50 (1.5% × $100.00) | Total with fees = $105.49

#### **c) POST /api/v1/preflight**

Purpose: Pre-authorize a transaction. The terminal sends the total plus fees along with a merchant-signed JWT to con�rm the transaction is being submitted for approval.

REQUEST

|`totalWithFees`|Decimal — Total amount including all fees|
|---|---|
|`totalSale`|Decimal — Original sale amount|
|`merchantJwt`|String — Signed JWT from terminal enrollment key|
|`terminalId`|String — Terminal identi�er|



###### RESPONSE

|`preflightId`|String — Unique identi�er for this preflight session|
|---|---|
|`status`|String — `GO` or `NO_GO`|
|`reason`|String — Reason if NO_GO (e.g., "terminal suspended", "daily limit exceeded")|



#### **d) POST /api/v1/con�rmation**

Purpose: Con�rm an approved card transaction. Sends the Visa NIT or Mastercard tracking number, the approval code, and transaction totals. Returns the stablecoin wallet details for printing a paper wallet.

|REQUEST||
|---|---|
|`preflightId`|String — Preflight session identi�er|
|`networkTrackingId`|String — Visa NIT or Mastercard tracking number|
|`approvalCode`|String — Authorization approval code from issuer|
|`totalSale`|Decimal — Original sale amount|
|`totalWithFees`|Decimal — Total amount including fees|
|`maskedPan`|String — Masked primary account number (e.g., `****1234`)|
|`cardholderName`|String — Cardholder name from Track 1 (if available)|
|`expirationDate`|String — Card expiration date (YYMM)|
|`entryMode`|String — POS entry mode: `CHIP`, `SWIPE`, `TAP`, or `MANUAL`|
|`cardBrand`|String — Card brand: `VISA`, `MASTERCARD`, `DISCOVER`, etc.|
|`aid`|String — EMV Application Identi�er (chip transactions only)|
|`applicationLabel`|String — EMV application label (e.g., "VISA DEBIT")|



|RESPONSE||
|---|---|
|`walletAddress`|String — Public address of the stablecoin wallet (for paper wallet QR)|
|`walletPrivateKey`|String — Private key for the stablecoin wallet (for paper wallet QR)|
|`stablecoinAmount`|Decimal — Amount of stablecoin loaded|
|`receiptHeader`|String — Merchant header text for receipt printing|
|`receiptFooter`|String — Merchant footer text for receipt printing|
|`transactionId`|String — GeniusPay transaction reference|



#### **e) POST /api/v1/cancel**

Purpose: Cancel a previously pre-flighted transaction that was not approved by the card network. Sends the rejection codes from Visa or Mastercard.

|REQUEST|
|---|


|`preflightId`|String — Preflight session identi�er|
|---|---|
|`networkTrackingId`|String — Visa NIT or Mastercard tracking number (if available)|
|`rejectionCode`|String — Network decline/rejection code (e.g., "05" = Do Not Honor)|
|`rejectionReason`|String — Human-readable decline reason|



|RESPONSE||
|---|---|
|`status`|String — `CANCELLED`|
|`preflightId`|String — Con�rmed preflight ID that was cancelled|



#### **f) POST /api/v1/sweep**

Purpose: Sweep stablecoin from a bearer instrument (paper wallet) into the merchant stablecoin account.

|REQUEST||
|---|---|
|`walletAddress`|String — Bearer instrument wallet address|
|`privateKey`|String — Private key from scanned QR code|
|`merchantId`|String — Merchant identi�er|



|RESPONSE||
|---|---|
|`status`|String — `SWEPT` or `FAILED`|
|`amountSwept`|Decimal — Stablecoin amount transferred to merchant account|
|`merchantBalance`|Decimal — Updated merchant stablecoin balance|
|`transactionId`|String — Sweep transaction identi�er|
|`blockchainTxHash`|String — On-chain transaction hash|



#### **g) POST /api/v1/redeem**

Purpose: Redeem stablecoin from the merchant account to �at via ACH from the GP operating account.

|REQUEST||
|---|---|
|`merchantId`|String — Merchant identi�er|
|`amount`|Decimal — Stablecoin amount to redeem|
|`bankAccountId`|String — Merchant bank account for ACH deposit|



###### RESPONSE

|`status`|String — `PENDING`, `PROCESSING`, or `FAILED`|
|---|---|
|`redemptionId`|String — Redemption transaction identi�er|
|`amountRedeemed`|Decimal — Stablecoin amount redeemed|
|`fiatAmount`|Decimal — Fiat equivalent being transferred|
|`estimatedSettlement`|String — Estimated ACH settlement date|
|`merchantBalance`|Decimal — Remaining merchant stablecoin balance|


#### **h) GET /api/v1/merchantAddress**

Purpose: Retrieve the Merchant stablecoin wallet address for display as a QR code on the N82 terminal. Used in GP App Mode 2 (Direct Wallet Payment) so a Customer can scan and send Payment Stablecoin (PSC) from their own wallet.

|REQUEST||
|---|---|
|`merchantId`|String — Merchant identi�er|
|RESPONSE||
|`merchantAddress`|String — Merchant stablecoin wallet address (for QR code display)|
|`network`|String — Blockchain network (e.g., `ethereum`, `polygon`)|
|`tokenContract`|String — Payment Stablecoin (PSC) contract address|
|`merchantName`|String — Merchant display name (for QR label)|



#### **i) GET /api/v1/paymentStatus**

Purpose: Con�rm on-chain payment status for a stablecoin transaction sent to the Merchant address. Uses Ethpar websocket for instant con�rmation — the POS subscribes and receives real-time �nality without waiting for multiple block con�rmations.

|REQUEST||
|---|---|
|`merchantId`|String — Merchant identi�er|
|`txHash`|String — On-chain transaction hash (optional; omit to check latest pending)|



|RESPONSE||
|---|---|
|`status`|String — `PENDING`, `CONFIRMED`, or `FAILED`|
|`confirmations`|Integer — Number of block con�rmations|
|`ethparFinality`|Boolean — `true` if Ethpar instant con�rmation received|
|`amount`|Decimal — PSC amount received|
|`fromAddress`|String — Sender wallet address|
|`txHash`|String — On-chain transaction hash|
|`timestamp`|String — ISO 8601 timestamp of con�rmation|
